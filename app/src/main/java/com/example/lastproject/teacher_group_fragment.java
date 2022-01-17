package com.example.lastproject;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class teacher_group_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<User> StudentsList;
    MyGroupAdapter adapter;

    private static final String TAG = "Student";

    DatabaseReference post_ref, student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_teacher_group_fragment,container,false);

        StudentsList = new ArrayList<User>();

        recyclerView = view.findViewById(R.id.group_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new MyGroupAdapter(StudentsList, getActivity());


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        post_ref = FirebaseDatabase.getInstance().
                getReference("Groups");
        student = FirebaseDatabase.getInstance().
                getReference("Users");

        retrieveData();

        Log.d(TAG, "StudentsList: " + StudentsList);
        //Student list пустой
        return view;

    }

    private void retrieveData() {

        String teaceherId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        post_ref.child(teaceherId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                StudentsList.clear();
                for (DataSnapshot tmp : snapshot.getChildren()) {
                    PersonInGroup st = tmp.getValue(PersonInGroup.class);
                    String idOfstident = st.getId_of_student();

                    student.child(idOfstident).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User p = snapshot.getValue(User.class);

                            StudentsList.add(p);

                            recyclerView.setAdapter(adapter);
                            Log.d(TAG, "StudentsList2: " + StudentsList);
                            //Student list тут есть ученики

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                Log.d(TAG, "StudentsList3: " + StudentsList);
                //Student list пустой


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no interesting in our purpose in the lesson
            }
        });

    }
}