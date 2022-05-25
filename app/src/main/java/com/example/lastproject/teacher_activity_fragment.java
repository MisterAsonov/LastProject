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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class teacher_activity_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Activitie> activitie_list;
    ArrayList<String> keys;
    teacher_activitie_adapter adapter;

    DatabaseReference act_ref;

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

        act_ref = FirebaseDatabase.getInstance().
                getReference("Travels");

        activitie_list = new ArrayList<Activitie>();
        keys = new ArrayList<String>();
        activitie_list.clear();


        recyclerView = view.findViewById(R.id.teacher_activities_rv);


        adapter = new teacher_activitie_adapter(activitie_list, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recyclerView.setAdapter(adapter);

        retrieveData();


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    Activitie deleted = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.RIGHT:
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

            }

        }

        @Override
        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,float dX, float dY,int actionState, boolean isCurrentlyActive){

           new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                   .addSwipeRightLabel("Delite")

                   .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                   .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_white_24)
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


    private void retrieveData() {
        String MyID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        act_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                activitie_list.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Activitie p = data.getValue(Activitie.class);
                    if(p.getEvent_participants().contains(MyID)) {
                        activitie_list.add(p);
                        keys.add(data.getKey());

                        adapter.setKeys(keys);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no interesting in our purpose in the lesson
            }
        });
    }


}