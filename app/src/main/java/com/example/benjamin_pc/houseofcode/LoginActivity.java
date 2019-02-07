package com.example.benjamin_pc.houseofcode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    CallbackManager callbackManager = CallbackManager.Factory.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //is user loggedin
        //AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //boolean isLoggedIn = accessToken != null && !accessToken.isExpired();


        //Later do actual login
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        //Facebook login
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("logintest", "onsucces: " + loginResult.toString());

                    }


                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.d("logintest", "onError: " + exception.toString());
                    }
                });


    }


    //Facebook login Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
        startActivity(intent);
    }
}
