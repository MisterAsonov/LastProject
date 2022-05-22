package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class teacher_cr_travel_participants_list extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<User> StudentsList;
    ArrayList<String> keys;
    teacher_cr_travel_group_adapter adapter;
    ImageButton back,clear,save;

    DatabaseReference post_ref, student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_cr_travel_participants_list);

        StudentsList = new ArrayList<User>();
        keys = new ArrayList<String>();

        Intent data = getIntent();


        back = (ImageButton) findViewById(R.id.arrrow_btn);
        clear = (ImageButton) findViewById(R.id.clear_btn);
        save = (ImageButton) findViewById(R.id.save_btn);
        recyclerView = findViewById(R.id.rc_patic_group);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new teacher_cr_travel_group_adapter(StudentsList, this, data.getStringArrayListExtra("id"),new teacher_cr_travel_group_adapter.teacher_ivents() {
            @Override
            public void update_selections(int count) {
                if(count>0) {
                    back.setVisibility(View.INVISIBLE);
                    clear.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                }else{
                    back.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.INVISIBLE);
                    save.setVisibility(View.INVISIBLE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.ClearSelections();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent returnIntent = new Intent();
                            returnIntent.putStringArrayListExtra("id",adapter.getList());
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        post_ref = FirebaseDatabase.getInstance().
                getReference("Groups");
        student = FirebaseDatabase.getInstance().
                getReference("Users");

        retrieveData();



    }

    private void retrieveData() {

        String teaceherId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        student.child(teaceherId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User uid = snapshot.getValue(User.class);

                post_ref.child(uid.referal_link).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        StudentsList.clear();
                        for (DataSnapshot tmp : snapshot.getChildren()) {
                            if(tmp.getKey().equals("Moadon")) continue;
                            PersonInGroup st = tmp.getValue(PersonInGroup.class);
                            String idOfstident = st.getId_of_student();

                            student.child(idOfstident).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User p = snapshot.getValue(User.class);

                                    StudentsList.add(p);
                                    keys.add(tmp.getKey());

                                    adapter.setKeys(keys);
                                    recyclerView.setAdapter(adapter);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

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

    }
}