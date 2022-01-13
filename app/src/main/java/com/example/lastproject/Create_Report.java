package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Create_Report extends AppCompatActivity {

    EditText titel, room, building, desc;
    String  status, date;
    Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);

        titel = findViewById(R.id.report_titel);
        room = findViewById(R.id.room);
        building = findViewById(R.id.building);
        desc = findViewById(R.id.description);
        status = "In progress";
        date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());


        /**
         * Это надо как то передать в  ArrayList<Report> reportsList который находится в ReportFragment
         * и перенести это через succesful_report
         */

        btn_confirm = findViewById(R.id.btn_confirmreport);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Create_Report.this, succesful_report.class);
                intent.putExtra("titel", titel.getText().toString());
                intent.putExtra("room", room.getText().toString());
                intent.putExtra("building", building.getText().toString());
                intent.putExtra("desc", desc.getText().toString());
                intent.putExtra("status", status);
                intent.putExtra("date", date);
                startActivity(intent);
                finish();
            }
        });
    }
}