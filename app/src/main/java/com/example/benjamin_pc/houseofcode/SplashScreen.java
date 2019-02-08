package com.example.benjamin_pc.houseofcode;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.benjamin_pc.houseofcode.Activities.LoginActivity;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //splashscreen config
        EasySplashScreen config = new EasySplashScreen(SplashScreen.this    )
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withBackgroundColor(Color.parseColor(getString(R.string.light_blue)))
                .withLogo(R.drawable.chatroom)
                .withHeaderText("ChatRoom Til House of Code");

        //Set Text Color
        config.getHeaderTextView().setTextColor(Color.WHITE);

        //Set to view
        View view = config.create();

        //Set view to content view
        setContentView(view);

    }
}
