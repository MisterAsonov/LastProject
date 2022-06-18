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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class teacher_Student_Profile extends AppCompatActivity {

    ImageButton btn_back;
    TextView ETname, ETemail, ETwho;
    ImageView photo;
    String name, lastname, email, who, id;
    String key;
    String url_photo;
    DatabaseReference mPostReference;
    DatabaseReference user_ref;

    public boolean onCreateOptionsMenu(Menu menu) {

        if(who.equals("Student")) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.teacher_student_profile, menu);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delite_student:

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid();

                FirebaseDatabase.getInstance().getReference("Users").child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);

                        FirebaseDatabase.getInstance().getReference().child("Groups").child(user.getReferal_link()).child(key).addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                PersonInGroup person = snapshot.getValue(PersonInGroup.class);

                                Map<String, Object> User = new HashMap<>();
                                User.put("referal_link","");

                                FirebaseDatabase.getInstance().getReference("Users").child(person.getId_of_student()).updateChildren(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){

                                            mPostReference = FirebaseDatabase.getInstance().getReference().child("Groups").child(user.getReferal_link()).child(key);
                                            mPostReference.removeValue();

                                        }
                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                
                finish();
                return true;

            case R.id.change_student:
                    Intent intent = new Intent(teacher_Student_Profile.this, change_student_name.class);
                    intent.putExtra("name", name);
                    intent.putExtra("lastname", lastname);
                    intent.putExtra("UID", id);
                    startActivity(intent);

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

         btn_back = findViewById(R.id.arrow_btn);
         btn_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
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