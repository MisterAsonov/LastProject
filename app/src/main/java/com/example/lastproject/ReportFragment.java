package com.example.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ReportFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Report> reportsList;
    ReportAdapter adapter;
    FloatingActionButton fab_btn;


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

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Report> reportsList = new ArrayList<Report>();


        reportsList.add(new Report("Titel 1","Explanation 1","In progress","22.02.2021"));
        reportsList.add(new Report("Titel 2","Explanation 2","In progress","22.02.2021"));
        reportsList.add(new Report("Titel 3","Explanation 3","In progress","22.02.2021"));
        reportsList.add(new Report("Titel 3","Explanation 4","In progress","22.02.2021"));
        reportsList.add(new Report("Titel 5","Explanation 5","In progress","22.02.2021"));



        ReportAdapter adapter = new ReportAdapter(reportsList,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
