package com.example.lastproject;

import static com.example.lastproject.Utils.TABLE_NAME_REPORT;
import static com.example.lastproject.Utils.TABLE_REPORT_COL_TITEL;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import pl.droidsonroids.gif.GifImageView;

public class ReportFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Report> reportsList;
    ArrayList<String> keys;
    ReportAdapter adapter;
    FloatingActionButton fab_btn;

    GifImageView photo;

    boolean flag = false;

    DatabaseReference post_ref;
    SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.reports_fragment,container,false);

        db = getActivity().openOrCreateDatabase(Utils.DATABASE_NAME,android.content.Context.MODE_PRIVATE ,null);
        db.execSQL("delete from " + TABLE_NAME_REPORT);


        fab_btn = view.findViewById(R.id.fab_btn);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Create_Report.class);
                startActivity(intent);
            }
        });

        photo = view.findViewById(R.id.gifImage);

        reportsList = new ArrayList<Report>();
        keys = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new ReportAdapter(reportsList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        post_ref = FirebaseDatabase.getInstance().
                getReference("Reports");

        retrieveData();

        retriveDB();

        return view;
    }

    private void retriveDB() {
        reportsList.clear();
        Cursor cursor3 = db.rawQuery("select * from " + TABLE_NAME_REPORT, null);
        while (cursor3.moveToNext()) {

            String titel = cursor3.getString(0);
            String explanation = cursor3.getString(1);
            String status = cursor3.getString(2);
            String date = cursor3.getString(3);
            String room = cursor3.getString(4);
            String building = cursor3.getString(5);
            String creator_id = cursor3.getString(6);

            Report report = new Report(titel, explanation, status, date, room,building,creator_id);

            reportsList.add(report);
            recyclerView.setAdapter(adapter);
        }

        if(reportsList.isEmpty()){
            photo.setVisibility(View.VISIBLE);
        }else {
            photo.setVisibility(View.INVISIBLE);
        }

    }


    private void retrieveData() {

        String creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        post_ref.child(creatorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                keys.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Report p = data.getValue(Report.class);

                    db.execSQL("insert into tbl_report values('" + p.getTitle() + "','" + p.getExplanation() + "','" + p.getStatus() + "','"
                            + p.getDate() + "','" + p.getRoom() + "','" + p.getBuilding() + "','" + p.getCreator_id() + "')");

                    keys.add(data.getKey());

                    adapter.setKeys(keys);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //no interesting in our purpose in the lesson
            }
        });
    }


}
