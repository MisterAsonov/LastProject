package com.example.lastproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Report> reportsList;
    ReportAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.reports_fragment,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Report> reportsList = new ArrayList<Report>();


        reportsList.add(new Report("No water in the room","Explanation","In progress","22.02.2021"));
        reportsList.add(new Report("No water in the room","Explanation","In progress","22.02.2021"));
        reportsList.add(new Report("No water in the room","Explanation","In progress","22.02.2021"));
        reportsList.add(new Report("No water in the room","Explanation","In progress","22.02.2021"));
        reportsList.add(new Report("No water in the room","Explanation","In progress","22.02.2021"));
        reportsList.add(new Report("No water in the room","Explanation","In progress","22.02.2021"));
        reportsList.add(new Report("No water in the room","Explanation","In progress","22.02.2021"));
        reportsList.add(new Report("No water in the room","Explanation","In progress","22.02.2021"));

        ReportAdapter adapter = new ReportAdapter(reportsList,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
