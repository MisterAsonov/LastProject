package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class teacher_Student_Profile extends AppCompatActivity {
    EditText ETname, ETlastname;
    TextView ETemail, ETwho;
    String name, lastname, email, who, id;
    String key;
    String url_photo;
    DatabaseReference mPostReference;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.studet_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(teacher_Student_Profile.this, "Enter", Toast.LENGTH_SHORT).show();
        if(item.getItemId() == R.id.delete){

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userID = user.getUid();
            mPostReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(userID).child(key);
            mPostReference.removeValue();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_profile);

        setSupportActionBar(findViewById(R.id.toolbar2));
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
        key = intent.getStringExtra("key");
        url_photo = intent.getStringExtra("url_photo");

        ETname.setText(name);
        ETlastname.setText(lastname);
        ETemail.setText(email);
        ETwho.setText(who);
    }
}