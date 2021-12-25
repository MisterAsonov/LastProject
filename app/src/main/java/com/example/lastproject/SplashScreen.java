package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    Animation rotate;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        handler = new Handler();


        logo = findViewById(R.id.SplashScreen_logo);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, HelloScreen.class);
                startActivity(intent);
                finish();

            }
        },5000);


        rotate = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.rotate);

        logo.setAnimation(rotate);
    }
}