package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class change_travel extends AppCompatActivity implements TimePickerFragment.TimePickerListener, DatePickerFragment.DatePickerListener {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int SelectObjectRecuest = 789;

    ArrayList<String> event_participants = new ArrayList<>();
    ImageFilterView photo;
    EditText ETtitel, ETlocation, ETdesc;
    TextView TVdate, TVtime;
    Button btn_save, brn_invite, btn_change_image;
    Handler handler;
    teacher_cr_act_participant_adapter adapter;
    RecyclerView recyclerView;

    String key,url_photo;

    private StorageReference mStorageRef;
    private Uri mImageUri;
    DatabaseReference event_ref;
    private StorageTask mUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_travel);

        photo = findViewById(R.id.image_of_change_event);
        ETtitel = findViewById(R.id.change_event_title);
        ETlocation = findViewById(R.id.change_trip_location);
        ETdesc = findViewById(R.id.change_disc_of_event);
        TVdate = findViewById(R.id.change_trip_date);
        TVtime = findViewById(R.id.change_trip_time);

        btn_save = findViewById(R.id.change_event_btn_update);
        brn_invite= findViewById(R.id.change_trip_btn_invite);

        btn_change_image = findViewById(R.id.change_trip_btn_change_image);
        recyclerView = findViewById(R.id.change_trip_recycler_invite);

        event_ref = FirebaseDatabase.getInstance().
                getReference("Travels");
        mStorageRef = FirebaseStorage.getInstance().getReference("Travels");

        Intent intent = getIntent();

        key = intent.getStringExtra("change_trip_key");
        String titel = intent.getStringExtra("change_event_titel");
        String location = intent.getStringExtra("change_event_location");
        String date = intent.getStringExtra("change_event_date");
        String time = intent.getStringExtra("change_event_time");
        String desc = intent.getStringExtra("change_event_desc");
        url_photo = intent.getStringExtra("change_event_imageUrl");
        event_participants = intent.getStringArrayListExtra("change_event_participants");

        ETtitel.setText(titel);
        ETdesc.setText(desc);
        TVdate.setText(date);
        TVtime.setText(time);
        ETlocation .setText(location);

        Animation fade_in = AnimationUtils.loadAnimation(change_travel.this, R.anim.fade_in);
        Animation fade_out = AnimationUtils.loadAnimation(change_travel.this, R.anim.fade_out);

        handler = new Handler();

        Toast.makeText(change_travel.this, "Click on photo to change an image", Toast.LENGTH_SHORT).show();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שמשנה שת הראות שלכפתור על ידי למיצה על תמונה
             */
            public void onClick(View view) {
                btn_change_image.startAnimation(fade_in);
                btn_change_image.setVisibility(View.VISIBLE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        btn_change_image.startAnimation(fade_out);
                        btn_change_image.setVisibility(View.INVISIBLE);


                    }
                }, 2000);

            }
        });

        btn_change_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        if(!url_photo.equals(""))
            Picasso.get().load(url_photo).into(photo);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(change_travel.this,6, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new teacher_cr_act_participant_adapter(event_participants, change_travel.this);

        recyclerView.setAdapter(adapter);

        brn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שמפנה למסך עם רשימת המשתתפים בטיול
             */
            public void onClick(View view) {
                Intent intent = new Intent(change_travel.this, teacher_cr_travel_participants_list.class);
                intent.putStringArrayListExtra("id", event_participants);
                startActivityForResult(intent,SelectObjectRecuest);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (ETtitel.getText().toString().isEmpty()) {
                    ETtitel.setError("Title is empty!");
                    ETtitel.requestFocus();
                    return;
                }

                if(ETtitel.length() < 4){
                    ETtitel.setError("Min titel length should be 4 characters!");
                    ETtitel.requestFocus();
                    return;
                }

                if (ETlocation.getText().toString().isEmpty()) {
                    ETlocation.setError("Location is empty!");
                    ETlocation.requestFocus();
                    return;
                }

                if (ETdesc.getText().toString().isEmpty()) {
                    ETdesc.setError("Description is empty!");
                    ETdesc.requestFocus();
                    return;
                }

                if (TVdate.getText().toString().isEmpty()) {
                    TVdate.setError("Date is empty!");
                    TVdate.requestFocus();
                    return;
                }

                if (TVtime.getText().toString().isEmpty()) {
                    TVtime.setError("Time is empty!");
                    TVtime.requestFocus();
                    return;
                }

                if (mImageUri != null) {
                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(mImageUri));

                    fileReference.putFile(mImageUri)
                            .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                                    if (task.isSuccessful()) {
                                        return fileReference.getDownloadUrl();
                                    } else {
                                        Toast.makeText(change_travel.this, "Error", Toast.LENGTH_SHORT).show();
                                        throw task.getException();

                                    }
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String url = task.getResult().toString();

                            if (!task.isSuccessful()) {
                                Toast.makeText(change_travel.this, "Error", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            updateTravel(ETtitel.getText().toString().trim(), ETlocation.getText().toString().trim(), TVdate.getText().toString().trim(), TVtime.getText().toString().trim(),
                                    ETdesc.getText().toString().trim(), url, event_participants);

                        }


                    });
                }else {
                    updateTravel(ETtitel.getText().toString().trim(), ETlocation.getText().toString().trim(), TVdate.getText().toString().trim(), TVtime.getText().toString().trim(),
                            ETdesc.getText().toString().trim(), url_photo, event_participants);
                }


            }
        });

        TVtime.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שמציגה את הTimePicker
             */
            public void onClick(View view) {

                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.setCancelable(false);

                timePickerFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        TVdate.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שמציגה את הDatePicker
             */
            public void onClick(View view) {

                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setCancelable(false);

                datePickerFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

    }

    /**
     * מחלקה ששומרת שינוים של הטיול במסד נתונים
     */
    private void updateTravel(String titel, String location, String date, String time, String desc, String url, ArrayList<String> event_participants) {
        Map<String, Object> activitie = new HashMap<>();

        activitie.put("event_date",date);
        activitie.put("event_desc",desc);
        activitie.put("event_location",location);
        activitie.put("event_participants",event_participants);
        activitie.put("event_time",time);
        activitie.put("event_title",titel);
        activitie.put("imageUrl",url);

        event_ref.child(key).updateChildren(activitie).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    Toast.makeText(change_travel.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                    finish();
                }else {

                    Toast.makeText(change_travel.this,"Failed to Update",Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    /**
     * מחלקה שמאפשרת בחירה לתמונת הטיול מזיכרון של טלפון
     */
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(photo);

            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(change_travel.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                //nothing
            }
        } else if (requestCode == SelectObjectRecuest && resultCode == RESULT_OK
                && data != null) {


            ArrayList<String> test = data.getStringArrayListExtra("id");
            event_participants.clear();
            event_participants.addAll(test);
            adapter.notifyDataSetChanged();

        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        TVtime.setText(hour + ":" + minute);

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        i1 = i1 + 1;
        TVdate.setText(i2 + "/" + i1 + "/" + i);
    }
}