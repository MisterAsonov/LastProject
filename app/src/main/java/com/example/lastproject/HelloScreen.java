package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.util.Hex;

public class HelloScreen extends AppCompatActivity {
    Button btn_start;
    ImageView logo;
    TextView text;
    boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hello_screen);

        btn_start = findViewById(R.id.btn_start);
        logo = findViewById(R.id.hello_logo);
        text = findViewById(R.id.textView2);

        SharedPreferences prefs = getSharedPreferences("your_key", MODE_PRIVATE);
        flag = prefs.getBoolean("First_open", false);

/**
 * מחלקה בודרת האם משצמש נכנס לאפליקציה פעם ראשונה אם כן מציגה את המסך אם לא מפנה למסך כניסה
 */
        if(!flag) {
                    flag = true;
                    SharedPreferences.Editor editor = getSharedPreferences("your_key", MODE_PRIVATE).edit();
                    editor.putBoolean("First_open", true);
                    editor.apply();
                }
                else{
                    Intent intent = new Intent(HelloScreen.this, LoginScreen.class);
                    startActivity(intent);
                    finish();
                }



        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שמפנה למסך כניסה
             */
            public void onClick(View view) {

                Intent intent = new Intent(HelloScreen.this, LoginScreen.class);

                ActivityOptions options_Logo = ActivityOptions.makeSceneTransitionAnimation(HelloScreen.this, new Pair<View, String>(logo,"hello_logo"));

                startActivity(intent, options_Logo.toBundle());

            }
        });
    }
}