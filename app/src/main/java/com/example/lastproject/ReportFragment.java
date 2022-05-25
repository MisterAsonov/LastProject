package com.example.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Report> reportsList;
    ArrayList<String> keys;
    ReportAdapter adapter;
    FloatingActionButton fab_btn;
    boolean flag = false;

    DatabaseReference post_ref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.reports_fragment,container,false);

        fab_btn = view.findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Create_Report.class);
                startActivity(intent);
            }
        });

        reportsList = new ArrayList<Report>();
        keys = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new ReportAdapter(reportsList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        post_ref = FirebaseDatabase.getInstance().
                getReference("Reports");

        retrieveData();


        return view;
    }

    /**
     *9*
     */
    private void retrieveData() {
        String creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        post_ref.child(creatorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                reportsList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Report p = data.getValue(Report.class);

                    reportsList.add(p);
                    keys.add(data.getKey());

                    adapter.setKeys(keys);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no interesting in our purpose in the lesson
            }
        });
    }
}
