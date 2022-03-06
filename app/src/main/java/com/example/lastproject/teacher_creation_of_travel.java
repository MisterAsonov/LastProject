package com.example.lastproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class teacher_creation_of_travel extends AppCompatActivity implements TimePickerFragment.TimePickerListener{
    EditText Titel;
    TextView creation_travel_time, creation_travel_date, et_place;
    TimePicker timePicker;
    DatePicker datePicker;
    Button btn_datepicker, btn_timepicker;
    CardView cardView,cardView2;
    String strDate;

    ImageView btn_place;

    Spinner spinner;
    String who;

    boolean flag = false;
    boolean flag2 = false;

    int hour, minute, PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_creation_of_travel);


        btn_datepicker = findViewById(R.id.btn_datepicker);
        btn_timepicker = findViewById(R.id.btn_timepicker);
        btn_place = findViewById(R.id.btn_place);

        et_place = findViewById(R.id.editText2);

        btn_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(teacher_creation_of_travel.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }
        });

        Titel = findViewById(R.id.creation_travel_titel);

        spinner = findViewById(R.id.creation_of_travel_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                teacher_creation_of_travel.this,
                R.array.notyfi,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                who = adapterView.getItemAtPosition(i).toString();
                if(who.equals("Yes")){

                    //Send notification to selected people

                }else{
                   //Nothing
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        creation_travel_time = findViewById(R.id.creation_travel_time);
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        creation_travel_time.setText(currentTime);

        creation_travel_date = findViewById(R.id.creation_travel_date);
        String currentDate = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        creation_travel_date.setText(currentDate);

        timePicker = findViewById(R.id.creation_travel_timepicker);
        datePicker = findViewById(R.id.creation_travel_datepicker);
        cardView2 = findViewById(R.id.yeyey);
        cardView = findViewById(R.id.zzyyz);



        btn_timepicker.setVisibility(View.GONE);
        btn_datepicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);
        datePicker.setVisibility(View.GONE);
        cardView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

        creation_travel_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flag2)
                {
                    datePicker.setVisibility(View.VISIBLE);
                    btn_datepicker.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.GONE);
                    btn_timepicker.setVisibility(View.GONE);
                    flag2 = true;
                    flag = false;
                } else {
                    datePicker.setVisibility(View.GONE);
                    btn_datepicker.setVisibility(View.GONE);
                    flag2 = false;
                    flag = true;
                }
                cardView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

               btn_datepicker.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       int day = datePicker.getDayOfMonth();
                       int month = datePicker.getMonth() + 1;
                       int year = datePicker.getYear();

                       creation_travel_date.setText(day + "." + month + "." + year);

                       datePicker.setVisibility(View.GONE);
                       btn_datepicker.setVisibility(View.GONE);
                       flag2 = false;
                       flag = false;

                   }
               });

            }
        });

        creation_travel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!flag)
                {
                    timePicker.setVisibility(View.VISIBLE);
                    btn_timepicker.setVisibility(View.VISIBLE);
                    datePicker.setVisibility(View.GONE);
                    btn_datepicker.setVisibility(View.GONE);
                    flag = true;
                    flag2 = false;

                } else {
                    timePicker.setVisibility(View.GONE);
                    btn_timepicker.setVisibility(View.GONE);
                    flag = false;
                    flag2 = true;

                }
                cardView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

                btn_timepicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            onTimeSet(timePicker ,timePicker.getHour(),timePicker.getMinute());
                            timePicker.setVisibility(View.GONE);
                            btn_timepicker.setVisibility(View.GONE);
                            flag = false;
                            flag2 = false;
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                StringBuilder stringBuilder = new StringBuilder();
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                stringBuilder.append("LATITUDE :");
                stringBuilder.append(latitude);
                stringBuilder.append("\n ");
                stringBuilder.append("longitude :");
                stringBuilder.append(longitude);
                et_place.setText(stringBuilder.toString());
            }
        }

    }



    @Override
    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        hour = selectedHour;
        minute = selectedMinute;
        creation_travel_time.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
    }

}