package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class change_student_name extends AppCompatActivity {

    EditText ETname, ETlastname;
    ImageButton back, save;

    String name, lastname, id;

    DatabaseReference user_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_student_name);


        ETname = findViewById(R.id.change_student_name_change);
        ETlastname = findViewById(R.id.change_student_password_change);
        back = findViewById(R.id.btn_csn_back);
        save = findViewById(R.id.btn_csn_save);

        Intent intent = getIntent();

        user_ref = FirebaseDatabase.getInstance().
                getReference("Users");

        name = intent.getStringExtra("name");
        lastname = intent.getStringExtra("lastname");
        id = intent.getStringExtra("UID");

        ETname.setText(name);
        ETlastname.setText(lastname);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ETname.getText().toString().trim();
                String lastname = ETlastname.getText().toString().trim();

                Map<String, Object> User = new HashMap<>();


                if (name.isEmpty()) {
                    ETname.setError("Name is required!");
                    ETname.requestFocus();
                    return;
                }

                if (lastname.isEmpty()) {
                    ETlastname.setError("Last name is required!");
                    ETlastname.requestFocus();
                    return;
                }

                User.put("name", name);
                User.put("lastname", lastname);

                user_ref.child(id).updateChildren(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    /**
                     * מחלקה שמשנה את השם ושם המשפחה של החניך במסד נתונים
                     */
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(change_student_name.this, Teacher_Main_Screen.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(change_student_name.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}