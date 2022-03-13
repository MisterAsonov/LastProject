package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class teacher_Student_Profile extends AppCompatActivity {

    ImageButton btn_back;
    FloatingActionButton btn_add_photo;
    TextView ETname, ETemail, ETwho;
    ImageView photo;
    String name, lastname, email, who, id;
    String key;
    String url_photo;
    DatabaseReference mPostReference;
    DatabaseReference user_ref;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher_student_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delite_student:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid();
                mPostReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(userID).child(key);
                mPostReference.removeValue();
                return true;

            case R.id.change_student:
                /**
                 * FirebaseAuth.getInstance().signOut();
                 *                 Intent intent = new Intent(teacher_Student_Profile.this, LoginScreen.class);
                 *                 startActivity(intent);
                 */

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_student_profile);

         ETname = findViewById(R.id.name_of_student_profile);
         ETemail = findViewById(R.id.email);
         ETwho = findViewById(R.id.who20);
         photo = findViewById(R.id.photo_of_student_profile);

        btn_add_photo = findViewById(R.id.float_btn_add_photo);
        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(teacher_Student_Profile.this, "Send message", Toast.LENGTH_SHORT).show();
            }
        });

         btn_back = findViewById(R.id.arrow_btn);
         btn_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(teacher_Student_Profile.this, Teacher_Main_Screen.class);
                 startActivity(intent);
             }
         });

        setSupportActionBar(findViewById(R.id.toolbar6));

        user_ref = FirebaseDatabase.getInstance().
                getReference("Users");

        Intent intent = getIntent();

        name = intent.getStringExtra("tv_name");
        lastname = intent.getStringExtra("tv_lname");
        email = intent.getStringExtra("tv_email");
        who = intent.getStringExtra("tv_who");
        id = intent.getStringExtra("tv_id");
        key = intent.getStringExtra("key");
        url_photo = intent.getStringExtra("url_photo");

        ETname.setText(name + " " + lastname);
        ETemail.setText(email);
        ETwho.setText(who);

        if(!url_photo.equals(""))
            Picasso.get().load(url_photo).into(photo);


    }
}