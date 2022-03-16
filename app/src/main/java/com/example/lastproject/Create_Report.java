package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Create_Report extends AppCompatActivity {

    EditText titel, room, building, desc;
    String  status, date, creatorID;
    Button btn_confirm;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);



        titel = findViewById(R.id.report_titel);
        room = findViewById(R.id.room);
        building = findViewById(R.id.building);
        desc = findViewById(R.id.description);
        status = "Waiting for confirmation";
        date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        btn_confirm = findViewById(R.id.btn_confirmreport);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(titel.getText().toString().isEmpty()){
                    titel.setError("Titel is empty!");
                    titel.requestFocus();
                    return;
                }

                if(room.getText().toString().isEmpty()){
                    room.setError("Room is empty!");
                    room.requestFocus();
                    return;
                }
                if(building.getText().toString().isEmpty()){
                    building.setError("Building is empty!");
                    building.requestFocus();
                    return;
                }
                if(desc.getText().toString().isEmpty()){
                    desc.setError("Description is empty!");
                    desc.requestFocus();
                    return;
                }

                writeNewReport(new Report(titel.getText().toString(), desc.getText().toString(), status,date,room.getText().toString(), building.getText().toString(), creatorID));
                finish();
            }
        });
    }

    public void writeNewReport(Report report) {

        FirebaseDatabase.getInstance().getReference("Reports").child(creatorID).push().setValue(report);

    }
}