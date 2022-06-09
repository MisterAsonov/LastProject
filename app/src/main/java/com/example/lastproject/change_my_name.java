package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class change_my_name extends AppCompatActivity {

    EditText ETname, ETlastname;
    ImageButton back, save;

    String creatorID;

    DatabaseReference user_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);


        ETname = findViewById(R.id.change_name_change);
        ETlastname = findViewById(R.id.change_password_change);
        back = findViewById(R.id.btn_cn_back);
        save = findViewById(R.id.btn_cn_save);

        user_ref = FirebaseDatabase.getInstance().
                getReference("Users");
        creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        user_ref.child(creatorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User p = snapshot.getValue(User.class);

                ETname.setText(p.getName());
                ETlastname.setText(p.getLastname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

                user_ref.child(creatorID).updateChildren(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    /**
                     * מחלקה שמשנה את השם ושם המשפחה של משתמש במסד נתונים
                     */
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            finish();

                        } else {

                            Toast.makeText(change_my_name.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }
}