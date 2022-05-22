package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class teacher_creation_of_travel extends AppCompatActivity implements TimePickerFragment.TimePickerListener, DatePickerFragment.DatePickerListener{
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int SelectObjectRecuest = 789;

    ArrayList<String> id = new ArrayList<>();
    ImageFilterView image;
    FloatingActionButton fab_add_image;
    EditText titel, location, desc;
    TextView date, time;
    Button btn_save, brn_invite, btn_change_image;
    Handler handler;
    teacher_cr_act_participant_adapter adapter;
    RecyclerView recyclerView;

    DatabaseReference user_ref;
    private StorageReference mStorageRef;

    private Uri mImageUri;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_creation_of_travel);

        image = findViewById(R.id.image_of_event);
        titel = findViewById(R.id.event_title);
        location = findViewById(R.id.set_location);
        desc = findViewById(R.id.disc_of_event);
        date = findViewById(R.id.start_date);
        time = findViewById(R.id.start_time);
        btn_save = findViewById(R.id.btn_save_trip);
        brn_invite = findViewById(R.id.btn_invite);
        btn_change_image = findViewById(R.id.btn_change_image);
        recyclerView = findViewById(R.id.recycler_invite);

        Animation fade_in = AnimationUtils.loadAnimation(teacher_creation_of_travel.this, R.anim.fade_in);
        Animation fade_out = AnimationUtils.loadAnimation(teacher_creation_of_travel.this, R.anim.fade_out);

        handler = new Handler();

        Toast.makeText(teacher_creation_of_travel.this, "Click on photo to change an image", Toast.LENGTH_SHORT).show();


        String creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(teacher_creation_of_travel.this,6, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new teacher_cr_act_participant_adapter(id, teacher_creation_of_travel.this);
        id.add(creatorID);

        recyclerView.setAdapter(adapter);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
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


        brn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(teacher_creation_of_travel.this, teacher_cr_travel_participants_list.class);
                intent.putStringArrayListExtra("id", id);
                startActivityForResult(intent,SelectObjectRecuest);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(teacher_creation_of_travel.this, "save", Toast.LENGTH_SHORT).show();
                //writeNewTravel(new Activitie(titel,location,date,time,desc,"",""));
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.setCancelable(false);

                timePickerFragment.show(getSupportFragmentManager(),"timePicker");
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setCancelable(false);


                datePickerFragment.show(getSupportFragmentManager(), "datePicker");

            }
        });

    }


    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(image);

            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(teacher_creation_of_travel.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                //nothing
            }
        }else if(requestCode == SelectObjectRecuest && resultCode == RESULT_OK
                && data != null ){


            ArrayList<String> test = data.getStringArrayListExtra("id");
            //id.clear();
            id.addAll(test);
            adapter.notifyDataSetChanged();

            Toast.makeText(teacher_creation_of_travel.this, test.toString() , Toast.LENGTH_SHORT).show();

        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        time.setText(hour + ":" + minute);
        Toast.makeText(teacher_creation_of_travel.this, String.valueOf(hour) + ":"+ String.valueOf(minute), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        date.setText(i2 + "/" + i1 + "/" + i);
    }
}