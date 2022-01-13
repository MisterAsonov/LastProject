package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class succesful_report extends AppCompatActivity {


    Button btn_myreports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesful_report);

        Intent intent = getIntent();
        String titel = intent.getStringExtra("titel").trim();
        String room = intent.getStringExtra("room").trim();
        String building = intent.getStringExtra("building").trim();
        String desc = intent.getStringExtra("desc").trim();
        String status = intent.getStringExtra("status").trim();
        String date = intent.getStringExtra("date").trim();

        //вот это всё надо перенести в ReportFragment

        btn_myreports = findViewById(R.id.btn_gotoreports);
        btn_myreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(succesful_report.this, ReportFragment.class);
                intent.putExtra("titel", titel);
                intent.putExtra("room", room);
                intent.putExtra("building", building);
                intent.putExtra("desc", desc);
                intent.putExtra("status", status);
                intent.putExtra("date", date);
                intent.putExtra("flag", true);
                startActivity(intent);
                finish();
            }
        });
    }
}