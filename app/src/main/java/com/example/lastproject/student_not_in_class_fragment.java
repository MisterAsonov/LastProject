package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class student_not_in_class_fragment extends Fragment{

    DatabaseReference user_ref;
    String ref_link;

    Button btn_scan;
    TextView enter;
    public static int SighAppTabRequestCode = 127;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_student_not_in_class_fragment,container,false);

        btn_scan = view.findViewById(R.id.btn_scan_qr_code);
        enter = view.findViewById(R.id.enter_invite_code);

        user_ref = FirebaseDatabase.getInstance().
                getReference("Users");

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), qr_scanner.class);

                startActivityForResult(intent, SighAppTabRequestCode );
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == SighAppTabRequestCode) {

            if(data != null && data.hasExtra("ref_link")) {
                ref_link = data.getStringExtra("ref_link");

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Map<String, Object> User = new HashMap<>();
                User.put("referal_link", ref_link);

                user_ref.child(userID).updateChildren(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            PersonInGroup person = new PersonInGroup(userID);
                            FirebaseDatabase.getInstance().getReference("Groups").child(ref_link).push()
                                    .setValue(person).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                    }
                                }

                                });
                        }

                    }
                });


            }


        }

    }
}