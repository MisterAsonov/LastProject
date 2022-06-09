package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password extends AppCompatActivity {
    ImageButton btn_back;
    Button btn_reset;
    EditText email;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_back = findViewById(R.id.btn_forgot_password_back);
        btn_reset = findViewById(R.id.btn_reset);
        email = findViewById(R.id.reset_email);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה סוגרת את הActivity
             */
            public void onClick(View view) {
                finish();
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();

                if(mail.isEmpty()){
                    email.setError("Email is required!");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Please provide valid email!");
                    email.requestFocus();
                    return;
                }

                reset(mail);
            }
        });


    }

    /**
     * מחלקה שעושה שיחזור סיסמה של משתמש על ידי מסד נתונים
     * @param mail
     */
    private void reset(String mail){
        auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()) {
                    Toast.makeText(forgot_password.this, "Pleas chek your inbox for password reset link", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(forgot_password.this,"Failed to reset the password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}