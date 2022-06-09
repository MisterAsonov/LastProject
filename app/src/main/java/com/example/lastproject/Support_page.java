package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Support_page extends AppCompatActivity {

    ImageButton back;
    TextView mail, telegram, insta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_page);

        back = findViewById(R.id.btn_contact_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שסוגרת את הActivity
             */
            public void onClick(View view) {
                finish();
            }
        });

        mail = findViewById(R.id.tv_mail);
        telegram = findViewById(R.id.tv_telegram);
        insta = findViewById(R.id.tv_insta);

        insta.setText("@" + "Fari_up");


    }
}