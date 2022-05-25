package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class report_for_teacher extends AppCompatActivity {

    TextView tv_titel, tv_desc, tv_building, tv_room, tv_date, tv_status;
    String status_report;
    String title, desc, building, room, date, creatorId, status;
    Spinner spinner;
    Button update;
    String key;

    DatabaseReference mPostReference;
    DatabaseReference databaseReference;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.studet_details_menu, menu);

        return true;
    }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete){

            mPostReference = FirebaseDatabase.getInstance().getReference().child("Reports").child(creatorId).child(key);
            mPostReference.removeValue();

            Intent intent = new Intent(report_for_teacher.this, Teacher_Main_Screen.class);
            startActivity(intent);
            finish();

            return true;

        }

        if(item.getItemId() == R.id.edit){

            update.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_for_teacher);

        setSupportActionBar(findViewById(R.id.toolbar2));

        tv_titel = findViewById(R.id.title_teacher_rs);
        tv_desc = findViewById(R.id.description_teacher_rs);
        tv_building = findViewById(R.id.building_teacher_rs);
        tv_room = findViewById(R.id.room_teacher_rs);
        tv_date = findViewById(R.id.date_teacher_rs);
        tv_status = findViewById(R.id.status_teacher_rs);
        spinner = findViewById(R.id.status);
        update = findViewById(R.id.btn_teacher_update);

        update.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.status,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status_report = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


            Intent intent = getIntent();

        title = intent.getStringExtra("report_title");
        desc = intent.getStringExtra("report_exp");
        building = intent.getStringExtra("report_building");
        room = intent.getStringExtra("report_room");
        date = intent.getStringExtra("report_date");
        status = intent.getStringExtra("report_status");
        key = intent.getStringExtra("key");
        creatorId = intent.getStringExtra("report_creator_id");

        tv_titel.setText(title);
        tv_desc.setText(desc);
        tv_building.setText(building);
        tv_room.setText(room);
        tv_date.setText(date);
        tv_status.setText(status);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> Report = new HashMap<>();

                Report.put("status", status_report);

                databaseReference = FirebaseDatabase.getInstance().getReference("Reports");

                databaseReference.child(creatorId).child(key).updateChildren(Report).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()){

                            Intent intent = new Intent(report_for_teacher.this, Teacher_Main_Screen.class);
                            startActivity(intent);
                            finish();

                            Toast.makeText(report_for_teacher.this,"Successfully Updated",Toast.LENGTH_SHORT).show();

                        }else {

                            Toast.makeText(report_for_teacher.this,"Failed to Update",Toast.LENGTH_SHORT).show();

                    }}
                });
            }
        });

    }
    }