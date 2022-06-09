package com.example.lastproject;




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

public class teacher_requests_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Report> reportsList;
    ArrayList<String> keys;
    ReportAdapter adapter;

    DatabaseReference post_ref,grup_ref, student;

    private static final String TAG = "Student";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_teacher_requests_fragment,container,false);

        reportsList = new ArrayList<Report>();
        keys = new ArrayList<>();

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

        Log.d(TAG, "reportsList: " + reportsList);
        return view;
    }

    /**
     * מחלקה שמקבלת רשימת הבקשות ממסד הנתונים
     */
    private void retrieveData() {
        String creatorID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        student.child(creatorID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User uid = snapshot.getValue(User.class);
                reportsList.clear();
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
                                        Log.d(TAG, "key: " + qwe.getKey());
                                        Report p = qwe.getValue(Report.class);

                                        keys.add(qwe.getKey());
                                        reportsList.add(p);

                                        adapter.setKeys(keys);
                                        recyclerView.setAdapter(adapter);
                                    }

                                    Log.d(TAG, "reportsList2: " + reportsList);

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