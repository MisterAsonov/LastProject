package com.example.lastproject;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

    DatabaseReference post_ref, student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_teacher_group_fragment,container,false);

        recyclerView = view.findViewById(R.id.group_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StudentsList = new ArrayList<User>();

        post_ref = FirebaseDatabase.getInstance().
                getReference("Groups");
        student = FirebaseDatabase.getInstance().
                getReference("Users");

        MyGroupAdapter adapter = new MyGroupAdapter(StudentsList, getActivity());

        retrieveData();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        return view;
    }

    private void retrieveData() {
        String teaceherId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        post_ref.child(teaceherId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                StudentsList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    PersonInGroup Student = snapshot.getValue(PersonInGroup.class);
                    String idOfstident = Student.getId_of_student();

                    //idOfstident пишет что он null

                    student.child(idOfstident).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User p = data.getValue(User.class);
                            StudentsList.add(p);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no interesting in our purpose in the lesson
            }
        });
    }
}