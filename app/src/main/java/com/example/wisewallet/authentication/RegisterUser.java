package com.example.wisewallet.authentication;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wisewallet.activities.CurrencyActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser {
    private final String name;
    private final String email;
    private final String password;
    private final Context context;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

    public RegisterUser(Context context, String name, String email, String password) {
        this.context = context;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void registration() {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = firebaseAuth.getCurrentUser(); // Move this line inside the onComplete callback

                    firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            nameRegistration();
                            Toast.makeText(context, "Email Verification Sent!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, CurrencyActivity.class);
                            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);


                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            String userId = firebaseAuth.getUid();

                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", name);

                            db.collection("users").document(userId)
                                    .set(userData)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // User information successfully stored
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle any errors while storing user information
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Error Sending Verification Email", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (firebaseAuth.fetchSignInMethodsForEmail(email).toString().equals("")) {
                        throwException();
                        Toast.makeText(context, "Already registered with the same email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle failure case
            }
        });
    }

    private void nameRegistration(){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name).build();
        firebaseUser.updateProfile(profileUpdates);

    }



    public void throwException(){
        Toast.makeText(context, "Please enter your Email in the correct format.", Toast.LENGTH_SHORT).show();

    }

}
