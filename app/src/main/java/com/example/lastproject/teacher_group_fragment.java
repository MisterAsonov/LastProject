package com.example.lastproject;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class teacher_group_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<User> StudentsList;
    MyGroupAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_teacher_group_fragment,container,false);

        recyclerView = view.findViewById(R.id.group_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<User> StudentsList = new ArrayList<User>();


        StudentsList.add(new User("Email 1","Name 1","Last Name 1","Student"));
        StudentsList.add(new User("Email 2","Name 2","Last Name 2","Student"));
        StudentsList.add(new User("Email 3","Name 3","Last Name 3","Student"));
        StudentsList.add(new User("Email 4","Name 4","Last Name 4","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));
        StudentsList.add(new User("Email 5","Name 5","Last Name 5","Student"));



        MyGroupAdapter adapter = new MyGroupAdapter(StudentsList, getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(adapter);


        return view;
    }
}