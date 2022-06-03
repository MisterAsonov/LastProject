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
import android.widget.TextView;
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
import java.util.Calendar;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class teacher_activity_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Activitie> activitie_list;
    ArrayList<String> keys;
    teacher_activitie_adapter adapter;
    TextView tv_moadon;

    DatabaseReference act_ref,user_ref,group_ref;

    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;

    boolean isOpen = false;

    Animation fab_open;
    Animation fab_close;
    Animation rotate_forward;
    Animation rotate_backward;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_teacher_fragment,container,false);


        tv_moadon = view.findViewById(R.id.tv_teacher_moadon);
        //Floating Action Buttons
        fab = view.findViewById(R.id.fab_teacher_add_activitie);
        fab1 = view.findViewById(R.id.fab1);
        fab2 = view.findViewById(R.id.fab2);


        //Animations
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFAB();
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
                Intent intent = new Intent(getActivity(), teacher_creation_of_travel.class);
                startActivity(intent);

            }
        });

        act_ref = FirebaseDatabase.getInstance().
                getReference("Travels");
        user_ref = FirebaseDatabase.getInstance().getReference("Users");
        group_ref = FirebaseDatabase.getInstance().getReference("Groups");

        activitie_list = new ArrayList<Activitie>();
        keys = new ArrayList<String>();



        recyclerView = view.findViewById(R.id.teacher_activities_rv);


        adapter = new teacher_activitie_adapter(activitie_list, getActivity());
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        retrieveData();
        retrieveData1();

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
                   .addSwipeRightLabel("Delete")

                   .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                   .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_white_24)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };




    private void animateFAB() {
        if(isOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen=false;
        }else{
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen=true;
        }
    }

    private void retrieveData() {
        String MyID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        act_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                activitie_list.clear();
                keys.clear();
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

    private void retrieveData1() {

        Calendar mCalendar = Calendar.getInstance();

        int year = mCalendar.get(Calendar.YEAR);

        int month = mCalendar.get(Calendar.MONTH);

        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user_ref.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User uid = snapshot.getValue(User.class);

                group_ref.child(uid.referal_link).child("Moadon").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Moadon moadon = snapshot.getValue(Moadon.class);

                        if(moadon.getYear() == year && moadon.getMonth() == month && moadon.getDay() == day) {

                            if (moadon != null) {

                                if (moadon.getMinute() != 0) {

                                    if (moadon.getMinute() < 10) {
                                        tv_moadon.setText("Group talk today at:  " + moadon.getHours() + ":0" + moadon.getMinute());
                                    }else{
                                        tv_moadon.setText("Group talk today at:  " + moadon.getHours() + ":" + moadon.getMinute());
                                    }

                                }else {
                                    tv_moadon.setText("Group talk today at:  " + moadon.getHours() + ":00");
                                }
                            }
                            else
                                tv_moadon.setVisibility(View.GONE);

                        }else{
                            tv_moadon.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //no interesting in our purpose in the lesson
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}