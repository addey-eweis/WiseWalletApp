package com.example.wisewallet.firebaseHandeling;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.wisewallet.activities.CurrencyActivity;
import com.example.wisewallet.activities.MainActivity;
import com.example.wisewallet.activities.SignActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirebaseOperationsManager extends Activity {
    private static FirebaseOperationsManager instance;
    private final Handler handler;
    private final FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private final String TAG = "Authentication_SignIn";

    private FirebaseOperationsManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        handler = new Handler(Looper.getMainLooper());

    }

    public static synchronized FirebaseOperationsManager getInstance() {
        if (instance == null) {
            instance = new FirebaseOperationsManager();
        }
        return instance;
    }

    public void authenticateUser(Context context, String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            throwException(context, "Please enter your Email and Password.");
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");

                        user = firebaseAuth.getCurrentUser();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    } else {
                        throwException(context, "Please enter the correct email or password.");
                    }
                });
    }

    public void throwException(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void checkAuthentication(Context context) {
        if(getUser() == null){
            redirectToLoginScreen(context);
        }
    }

    public boolean checkAuthenticationBoolean(){
        if(getUser() != null){
            return true;
        }
        return false;
    }

    public void redirectToLoginScreen(Context context) {
        Intent intent = new Intent(context, SignActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void registerUser(Context context, String name, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    user.sendEmailVerification().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            nameRegistration(name);
                            Toast.makeText(context, "Email Verification Sent!", Toast.LENGTH_SHORT).show();

                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            String userId = firebaseAuth.getCurrentUser().getUid();

                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", name);

                            db.collection("users").document(userId)
                                    .set(userData, SetOptions.merge())
                                    .addOnSuccessListener(aVoid -> {
                                        // User information successfully stored
                                        Intent intent = new Intent(context, CurrencyActivity.class);
                                        context.startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle any errors while storing user information
                                        Toast.makeText(context, "Error storing user information.", Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(context, "Error Sending Verification Email", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(e -> Toast.makeText(context, "Error Sending Verification Email", Toast.LENGTH_SHORT).show());
                } else {
                    Toast.makeText(context, "Already registered with the same email", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(e -> {
            // Handle failure case
            Toast.makeText(context, "Registration failed.", Toast.LENGTH_SHORT).show();
        });
    }

    private void nameRegistration(String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        user.updateProfile(profileUpdates);
    }

    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void submitToFirebase(Context context, DocumentReference documentReference, Map<String, Object> dataMap, FirebaseSubmitCallback callback){
        if (dataMap == null || documentReference == null) {
            throwException(context, "Invalid input data.");
            return;
        }

        handler.post(() -> {
            documentReference.set(dataMap, SetOptions.merge())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Data successfully submitted to Firebase
                            if (callback != null) {
                                callback.onSubmitSuccess();
                            }
                        } else {
                            // Error occurred while submitting data to Firebase
                            if (task.getException() != null) {
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error";


                                // Handle the error or display an error message to the user
                                 Toast.makeText(context, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();

                                if (callback != null) {
                                    callback.onSubmitFailure(errorMessage);
                                }
                            }
                        }
                    });
        });
    }

    public void getCurrency(Context context, FirebaseCurrencyCallback currencyCallback){
        DocumentReference currencyDocumentReference = FirebaseFirestore.getInstance().collection("users").document(getUserId(context));

        readFromFirebase(context, currencyDocumentReference, new FirebaseReadCallback() {
            @Override
            public void onDataRead(Map<String, Object> dataMap) {
                String currency = dataMap.get("currency").toString();
                if(currencyCallback != null){
                    currencyCallback.onCurrencyRead(currency);
                }

            }
        });
    }
    public interface FirebaseCurrencyCallback{
        void onCurrencyRead(String currency);
    }

    public interface FirebaseSubmitCallback {
        void onSubmitSuccess();
        void onSubmitFailure(String errorMessage);
    }

    public void readFromFirebase(Context context, DocumentReference documentReference, FirebaseReadCallback callback) {
        if (documentReference == null) {
            throwException(context, "Invalid input data.");
            return;
        }

        handler.post(() -> {
            documentReference.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        // Data successfully read from Firebase
                        // You can extract the data from the document using document.getData() or specific field access methods
                        // For example, if you have a field named "name":
                        // String name = document.getString("name");
                        // or
                        // Map<String, Object> dataMap = document.getData();
//                        Map<String, Object> dataMap = new HashMap<>();
//                        callback.onDataRead(dataMap); // Provide the data to the callback
                        if (callback != null) {
                            callback.onDataRead(document.getData());
                        }
                    } else {
                        // The document doesn't exist or is empty
                        // Handle the situation accordingly
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Error occurred while reading data from Firebase
                    if (task.getException() != null) {
                        String errorMessage = task.getException().getMessage();
                        // Handle the error or display an error message to the user
                        // For example:
                         Toast.makeText(context, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    public interface FirebaseReadCallback {
        void onDataRead(Map<String, Object> dataMap);
    }

    public String getUserId(Context context){
        checkAuthentication(context);
        return getUser().getUid();
    }
}
