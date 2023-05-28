package com.example.wisewallet.authentication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.wisewallet.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticateUser extends Activity {
    public final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String email;
    private String password;
    private final TextView statusText;
    private FirebaseUser user;
    private final Context context;
    final String TAG = "Authentication_SignIn";

    public AuthenticateUser(Context context, TextView statusText, String email, String password) {
        this.context = context;
        this.statusText = statusText;
        this.email = email;
        this.password = password;
    }

    public void authenticateUser(){
        if(!email.equals("") | !password.equals("")){
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                Log.d(TAG, "signInWithEmail:success");

                                user = firebaseAuth.getCurrentUser();
                                Intent intent = new Intent(context, MainActivity.class);
                                context.startActivity(intent);

                            }
                            else {
                                throwException();

                            }

                        }
                    });
        }

    }

    public void throwException(){
        statusText.setText("Please enter the correct email or password");
    }

    public FirebaseUser getUser() {
        return user;
    }
}
