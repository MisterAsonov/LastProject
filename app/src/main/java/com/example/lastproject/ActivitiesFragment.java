package com.example.lastproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivitiesFragment extends Fragment {
DatabaseReference group_ref,user_ref;
TextView tv_moadon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activities_fragment,container,false);

        group_ref = FirebaseDatabase.getInstance().getReference("Groups");
        user_ref = FirebaseDatabase.getInstance().getReference("Users");

        tv_moadon  = view.findViewById(R.id.textView6);

        retrieveData();

        return view;
    }

    private void retrieveData() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user_ref.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User uid = snapshot.getValue(User.class);

                group_ref.child(uid.referal_link).child("Moadon").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                      Moadon moadon = snapshot.getValue(Moadon.class);
                      if(moadon != null)
                        tv_moadon.setText("Moadon: " + moadon.getHours() + ":" + moadon.getMinute());
                      else
                          tv_moadon.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //no interesting in our purpose in the lesson
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
