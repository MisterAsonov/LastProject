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
    boolean flag = false;


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


        flag = getActivity().getIntent().getBooleanExtra("flag",true);

        if(flag){
            /**
             * программа крашиться из за того что в первый раз все эти значения null
             * но если я хочу что то передать из другого активити то ничего не работает
             */
            String titel = getActivity().getIntent().getExtras().getString("titel");
            String room = getActivity().getIntent().getExtras().getString("room");
            String building = getActivity().getIntent().getExtras().getString("building");
            String desc = getActivity().getIntent().getExtras().getString("desc");
            String status = getActivity().getIntent().getExtras().getString("status");
            String date = getActivity().getIntent().getExtras().getString("date");

            reportsList.add(new Report(titel,desc,status,date, room, building));

            flag = true;

        }



        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Report> reportsList = new ArrayList<Report>();


        ReportAdapter adapter = new ReportAdapter(reportsList,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
