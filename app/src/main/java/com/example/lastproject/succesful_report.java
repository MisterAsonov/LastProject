package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class succesful_report extends AppCompatActivity {
    Button btn_myreports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succesful_report);

        btn_myreports = findViewById(R.id.btn_gotoreports);
        btn_myreports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(succesful_report.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}