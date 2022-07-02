package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {

    private static final String TAG = "who";
    ImageView logo;
    Animation rotate;
    Handler handler;
    String whois;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();

        handler = new Handler();
        logo = findViewById(R.id.SplashScreen_logo);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email").trim();
        String password = intent.getStringExtra("password").trim();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //Не работает если есть пробел после почты например
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "enter " + task.isSuccessful());

                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            reference = FirebaseDatabase.getInstance().getReference("Users");
                            userID = user.getUid();

                            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User userProfile = snapshot.getValue(User.class);


                                    whois = userProfile.who;


                                    if (whois.equals("Teacher")) {

                                        Intent intent = new Intent(SplashScreen.this, Teacher_Main_Screen.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    if (whois.equals("Student")) {

                                        Log.d(TAG, "student");

                                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                    }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });






                        } else {

                            Log.d(TAG, "back");
                            Toast.makeText(SplashScreen.this, "Faild to enter! Check your email or password", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SplashScreen.this, LoginScreen.class);
                            startActivity(intent);
                            finish();


                            //Что бы на логин скрине появился тост что авторизация не прошла

                        }
                    }
                });


            }
        }, 3000);


        rotate = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.rotate);

        logo.setAnimation(rotate);
    }
}