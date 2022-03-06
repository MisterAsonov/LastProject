package com.example.lastproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class teacher_activity_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Activitie> activitie_list;
    ArrayList<String> keys;
    teacher_activitie_adapter adapter;

    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;

    private boolean FAB_Status = false;

    Animation show_fab_1;
    Animation hide_fab_1;
    Animation show_fab_2;
    Animation hide_fab_2;
    Animation show_fab_3;
    Animation hide_fab_3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_teacher_fragment,container,false);

        recyclerView = view.findViewById(R.id.teacher_activities_rv);

        //Floating Action Buttons
        fab = view.findViewById(R.id.fab_teacher_add_activitie);
        fab1 = view.findViewById(R.id.fab_1);
        fab2 = view.findViewById(R.id.fab_2);
        fab3 = view.findViewById(R.id.fab_3);

        //Animations
        show_fab_1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab1_show);
        hide_fab_1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab1_hide);
        show_fab_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab2_show);
        hide_fab_2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab2_hide);
        show_fab_3 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab3_show);
        hide_fab_3 = AnimationUtils.loadAnimation(getActivity(), R.anim.fab3_hide);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAB_Status == false) {
                    //Display FAB menu
                    expandFAB();
                    FAB_Status = true;
                } else {
                    //Close FAB menu
                    hideFAB();
                    FAB_Status = false;
                }

            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.setCancelable(false);
                timePickerFragment.show(getParentFragmentManager() , "timePicker");
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Floating Action Button 2", Toast.LENGTH_SHORT).show();
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), teacher_creation_of_travel.class);
                startActivity(intent);

            }
        });




        activitie_list = new ArrayList<Activitie>();
        keys = new ArrayList<String>();


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Trip","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Peilut","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Talk","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Trip","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Peilut","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Talk","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Trip","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Peilut","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Talk","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Trip","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Peilut","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Talk","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Trip","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Peilut","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Talk","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Trip","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Peilut","05.02.2022","07.02.2022","6"));
        activitie_list.add(new Activitie("Ramat HaGalan","Trip to Ramat HaGolan","Talk","05.02.2022","07.02.2022","6"));

 

        adapter = new teacher_activitie_adapter(activitie_list, getActivity());
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return view;
    }




    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();
        layoutParams.rightMargin = 0;
        layoutParams.bottomMargin = 0;
        layoutParams.rightMargin += (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(show_fab_1);
        fab1.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();
        layoutParams2.rightMargin = 0;
        layoutParams2.bottomMargin =0 ;
        layoutParams2.rightMargin += (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(show_fab_2);
        fab2.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();
        layoutParams3.rightMargin = 0;
        layoutParams3.bottomMargin = 0;
        layoutParams3.rightMargin += (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(show_fab_3);
        fab3.setClickable(true);
    }


    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fab1.getLayoutParams();

        layoutParams.rightMargin -= (int) (fab1.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (fab1.getHeight() * 0.25);
        fab1.setLayoutParams(layoutParams);
        fab1.startAnimation(hide_fab_1);
        fab1.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fab2.getLayoutParams();

        layoutParams2.rightMargin -= (int) (fab2.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (fab2.getHeight() * 1.5);
        fab2.setLayoutParams(layoutParams2);
        fab2.startAnimation(hide_fab_2);
        fab2.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) fab3.getLayoutParams();

        layoutParams3.rightMargin -= (int) (fab3.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (fab3.getHeight() * 1.7);
        fab3.setLayoutParams(layoutParams3);
        fab3.startAnimation(hide_fab_3);
        fab3.setClickable(false);
    }



}