package com.example.lastproject;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


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

        ArrayList<Integer> part = new ArrayList<>();
        part.add(132);
        part.add(153);
        part.add(489);
        part.add(789);
        part.add(134562);

        activitie_list.add(new Activitie("Shuka norda Stave vie Bu","Grand kanion","17/03/2022","9:00","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently" +
                " with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.","https://firebasestorage.googleapis.com/v0/b/lastproject-d86c0.appspot.com/o/Users%2F1647215392141.jpg?alt=media&token=84167bcc-89b6-4122-be7a-c9d54e8e26bf",part));

        activitie_list.add(new Activitie("Shuka norda Stave vie Bu","Grand kanion","17/03/2022","9:00","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently" +
                " with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.","https://firebasestorage.googleapis.com/v0/b/lastproject-d86c0.appspot.com/o/Users%2F1647215392141.jpg?alt=media&token=84167bcc-89b6-4122-be7a-c9d54e8e26bf",part));

        activitie_list.add(new Activitie("Shuka norda Stave vie Bu","Grand kanion","17/03/2022","9:00","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently" +
                " with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.","https://firebasestorage.googleapis.com/v0/b/lastproject-d86c0.appspot.com/o/Users%2F1647215392141.jpg?alt=media&token=84167bcc-89b6-4122-be7a-c9d54e8e26bf",part));

        activitie_list.add(new Activitie("Shuka norda Stave vie Bu","Grand kanion","17/03/2022","9:00","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently" +
                " with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "https://firebasestorage.googleapis.com/v0/b/lastproject-d86c0.appspot.com/o/Users%2F1647215392141.jpg?alt=media&token=84167bcc-89b6-4122-be7a-c9d54e8e26bf",part));

        activitie_list.add(new Activitie("Shuka norda Stave vie Bu","Grand kanion","17/03/2022","9:00","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently" +
                " with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "https://firebasestorage.googleapis.com/v0/b/lastproject-d86c0.appspot.com/o/Users%2F1647215392141.jpg?alt=media&token=84167bcc-89b6-4122-be7a-c9d54e8e26bf",part));

        activitie_list.add(new Activitie("Shuka norda Stave vie Bu","Grand kanion","17/03/2022","9:00","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently" +
                " with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "https://firebasestorage.googleapis.com/v0/b/lastproject-d86c0.appspot.com/o/Users%2F1647215392141.jpg?alt=media&token=84167bcc-89b6-4122-be7a-c9d54e8e26bf",part));

        activitie_list.add(new Activitie("Shuka norda Stave vie Bu","Grand kanion","17/03/2022","9:00","Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently" +
                " with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                "https://firebasestorage.googleapis.com/v0/b/lastproject-d86c0.appspot.com/o/Users%2F1647215392141.jpg?alt=media&token=84167bcc-89b6-4122-be7a-c9d54e8e26bf",part));


        adapter = new teacher_activitie_adapter(activitie_list, getActivity(), keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    Activitie deleted = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.LEFT:
                    deleted = activitie_list.get(position);
                    activitie_list.remove(position);
                    adapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, deleted.getEvent_title(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            activitie_list.add(position, deleted);
                            adapter.notifyItemRemoved(position);
                        }
                    }).show();

                    break;
                case ItemTouchHelper.RIGHT:

                    break;
            }

        }

        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){

           new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                   .addSwipeLeftLabel("Delite")

                   .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                   .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_white_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };




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