package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class teacher_Student_Profile extends AppCompatActivity {
    EditText ETname, ETlastname;
    TextView ETemail, ETwho;
    String name, lastname, email, who, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_profile);

        ETname = findViewById(R.id.et_name_of_student);
        ETlastname = findViewById(R.id.et_lastname_of_student);
        ETemail = findViewById(R.id.et_email_of_student);
        ETwho = findViewById(R.id.et_who_of_student);

        Intent intent = getIntent();

        name = intent.getStringExtra("tv_name");
        lastname = intent.getStringExtra("tv_lname");
        email = intent.getStringExtra("tv_email");
        who = intent.getStringExtra("tv_who");
        id = intent.getStringExtra("tv_id");

        ETname.setText(name);
        ETlastname.setText(lastname);
        ETemail.setText(email);
        ETwho.setText(who);
    }
}