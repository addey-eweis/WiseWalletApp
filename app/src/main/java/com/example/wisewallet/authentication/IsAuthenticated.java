package com.example.wisewallet.authentication;

import android.content.Context;
import android.content.Intent;

import com.example.wisewallet.activities.SignActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IsAuthenticated {
    private final Context context;

    public IsAuthenticated(Context context) {
        this.context = context;
    }

    public boolean checkAuthentication() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        // Check if the user is authenticated
        if (currentUser == null) {
            return false; // User is not authenticated
        }

        return true; // User is authenticated
    }

    public void redirectToLoginScreen() {
        Intent intent = new Intent(context, SignActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

