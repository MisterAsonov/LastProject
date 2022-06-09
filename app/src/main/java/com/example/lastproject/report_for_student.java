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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class report_for_student extends AppCompatActivity {

    EditText tv_titel, tv_desc, tv_building, tv_room;
    TextView  tv_date,et_status;
    String title, desc, building, room, date, status, creatorId;
    Button update;
    String key;

    DatabaseReference databaseReference;
    DatabaseReference mPostReference;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.studet_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.delete){
/**
 * פעולה שמסירה את הבקשה ממסד נתונים
 */
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userID = user.getUid();
            mPostReference = FirebaseDatabase.getInstance().getReference().child("Reports").child(userID).child(key);
            mPostReference.removeValue();


            finish();

            return true;

        }
/**
 * פעולה שמשנה את הגדר מופעל של שדות עם נושא, תיאור ,בניין, חדר
 */
        if(item.getItemId() == R.id.edit){

            tv_titel.setEnabled(true);
            tv_desc.setEnabled(true);
            tv_building.setEnabled(true);
            tv_room.setEnabled(true);

            update.setVisibility(View.VISIBLE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_for_student);

        setSupportActionBar(findViewById(R.id.toolbar2));

        tv_titel = findViewById(R.id.title_student_rs);
        tv_titel.setEnabled(false);

        tv_desc = findViewById(R.id.description_student_rs);
        tv_desc.setEnabled(false);

        tv_building = findViewById(R.id.building_student_rs);
        tv_building.setEnabled(false);

        tv_room = findViewById(R.id.room_student_rs);
        tv_room.setEnabled(false);

        tv_date = findViewById(R.id.date_student_rs);
        et_status = findViewById(R.id.status_student_rs);
        update = findViewById(R.id.btn_update_student);

        update.setVisibility(View.GONE);

        Intent intent = getIntent();

        title = intent.getStringExtra("report_title");
        desc = intent.getStringExtra("report_exp");
        building = intent.getStringExtra("report_building");
        room = intent.getStringExtra("report_room");
        date = intent.getStringExtra("report_date");
        status = intent.getStringExtra("report_status");
        creatorId = intent.getStringExtra("report_creator_id");
        key = intent.getStringExtra("key");

        tv_titel.setText(title);
        tv_desc.setText(desc);
        tv_building.setText(building);
        tv_room.setText(room);
        tv_date.setText(date);
        et_status.setText(status);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שעושה שינוי של בקשה במסד נתונים
             */
            public void onClick(View view) {
                Map<String, Object> Report = new HashMap<>();
                Report.put("title", tv_titel.getText().toString());
                Report.put("room", tv_room.getText().toString());
                Report.put("building", tv_building.getText().toString());
                Report.put("explanation", tv_desc.getText().toString());

                databaseReference = FirebaseDatabase.getInstance().getReference("Reports");

                databaseReference.child(creatorId).child(key).updateChildren(Report).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()){

                            Intent intent = new Intent(report_for_student.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(report_for_student.this,"Successfully Updated",Toast.LENGTH_SHORT).show();

                        }else {

                            Toast.makeText(report_for_student.this,"Failed to Update",Toast.LENGTH_SHORT).show();

                        }}
                });
            }
        });
    }
}