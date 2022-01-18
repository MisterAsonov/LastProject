package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class report_for_teacher extends AppCompatActivity {

    TextView tv_titel, tv_desc, tv_building, tv_room, tv_date;
    EditText et_status;
    String title, desc, building, room, date, status, creatorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_for_teacher);


        tv_titel = findViewById(R.id.titel_teacher_rs);
        tv_desc = findViewById(R.id.desc_teacher_rs);
        tv_building = findViewById(R.id.building_teacher_rs);
        tv_room = findViewById(R.id.room_teacher_rs);
        tv_date = findViewById(R.id.date_teacher_rs);
        et_status = findViewById(R.id.status_teacher_rs);

        Intent intent = getIntent();

        title = intent.getStringExtra("report_title");
        desc = intent.getStringExtra("report_exp");
        building = intent.getStringExtra("report_building");
        room = intent.getStringExtra("report_room");
        date = intent.getStringExtra("report_date");
        status = intent.getStringExtra("report_status");
        creatorId = intent.getStringExtra("report_creator_id");

        tv_titel.setText(title);
        tv_desc.setText(desc);
        tv_building.setText(building);
        tv_room.setText(room);
        tv_date.setText(date);
        et_status.setText(status);


    }
}