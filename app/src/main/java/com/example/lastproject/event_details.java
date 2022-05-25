package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class event_details extends AppCompatActivity {

    ImageButton btn_back;
    Button invite;
    ImageView photo;
    TextView tv_titel, tv_desc, tv_date, tv_time, tv_location;

    String titel, desc, date, time, location;
    String key;
    String url_photo;
    ArrayList<String> event_participants;

    teacher_cr_act_participant_adapter adapter;
    RecyclerView recyclerView;

    DatabaseReference mPostReference;
    DatabaseReference event_ref;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delite_event:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid();
                mPostReference = FirebaseDatabase.getInstance().getReference().child("Travels").child(key);
                mPostReference.removeValue();
                return true;

            case R.id.change_event:
                //nothing yet

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        tv_titel = findViewById(R.id.event_details_titel);
        tv_desc = findViewById(R.id.event_details_desc);
        tv_date = findViewById(R.id.event_details_date);
        tv_time = findViewById(R.id.event_details_time);
        tv_location = findViewById(R.id.event_details_location);
        invite = findViewById(R.id.btn_event_invite);
        recyclerView = findViewById(R.id.recycler_event_invite);
        photo = findViewById(R.id.event_detail_photo);

        btn_back = findViewById(R.id.btn_event_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setSupportActionBar(findViewById(R.id.event_toolbar));

        event_ref = FirebaseDatabase.getInstance().
                getReference("Travels");

        Intent intent = getIntent();

        titel = intent.getStringExtra("event_titel");
        location = intent.getStringExtra("event_location");
        date = intent.getStringExtra("event_date");
        time = intent.getStringExtra("event_time");
        desc = intent.getStringExtra("event_desc");
        url_photo = intent.getStringExtra("event_imageUrl");
        event_participants = intent.getStringArrayListExtra("event_participants");
        key = intent.getStringExtra("event_key");

        tv_titel.setText(titel);
        tv_desc.setText(desc);
        tv_date.setText(date);
        tv_time.setText(time);
        tv_location .setText(location);

        if(!url_photo.equals(""))
            Picasso.get().load(url_photo).into(photo);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(event_details.this,6, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new teacher_cr_act_participant_adapter(event_participants, event_details.this);

        recyclerView.setAdapter(adapter);
    }


}