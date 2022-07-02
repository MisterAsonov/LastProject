package com.example.lastproject;




import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;
import static com.example.lastproject.Utils.TABLE_NAME_REPORT;
import static com.example.lastproject.Utils.TABLE_REPORT_COL_TITEL;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class teacher_requests_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Report> reportsList;
    ArrayList<String> keys;
    ReportAdapter adapter;

    GifImageView photo;

    DatabaseReference post_ref,grup_ref, student;
    SQLiteDatabase db;

    private static final String TAG = "Student";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_teacher_requests_fragment,container,false);

        db = getActivity().openOrCreateDatabase(Utils.DATABASE_NAME,android.content.Context.MODE_PRIVATE ,null);
        db.execSQL("delete from " + TABLE_NAME_REPORT);

        reportsList = new ArrayList<Report>();
        keys = new ArrayList<>();

        photo = view.findViewById(R.id.gifImage4);

        recyclerView = view.findViewById(R.id.rv_requests);
        recyclerView.setHasFixedSize(true);

        adapter = new ReportAdapter(reportsList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        post_ref = FirebaseDatabase.getInstance().
                getReference("Reports");

        grup_ref = FirebaseDatabase.getInstance().
                getReference("Groups");

        student = FirebaseDatabase.getInstance().
                getReference("Users");

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
        student.child(creatorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User uid = snapshot.getValue(User.class);
                keys.clear();
                grup_ref.child(uid.referal_link).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot tmp : snapshot.getChildren()) {
                            if(tmp.getKey().equals("Moadon")) continue;
                            PersonInGroup st = tmp.getValue(PersonInGroup.class);
                            String idOfstident = st.getId_of_student();

                            post_ref.child(idOfstident).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot qwe : snapshot.getChildren()) {
                                        Report p = qwe.getValue(Report.class);

                                        db.execSQL("insert into tbl_report values('" + p.getTitle() + "','" + p.getExplanation() + "','" + p.getStatus() + "','"
                                                + p.getDate() + "','" + p.getRoom() + "','" + p.getBuilding() + "','" + p.getCreator_id() + "')");


                                        keys.add(qwe.getKey());

                                        adapter.setKeys(keys);

                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}