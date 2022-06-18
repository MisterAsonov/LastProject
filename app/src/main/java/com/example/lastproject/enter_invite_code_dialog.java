package com.example.lastproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class enter_invite_code_dialog extends DialogFragment {

    DatabaseReference user_ref;
    EditText ETinvite_code;
    Button btn_join;
    ImageButton back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_enter_invite_code_dialog, container, false);

        ETinvite_code = view.findViewById(R.id.invite_code);
        btn_join = view.findViewById(R.id.btn_join);
        back = view.findViewById(R.id.btn_invite_code_back);

        user_ref = FirebaseDatabase.getInstance().
                getReference("Users");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String invite_code = ETinvite_code.getText().toString().trim();

                if(invite_code.isEmpty()){
                    ETinvite_code.setError("Invite code is required!");
                    ETinvite_code.requestFocus();
                    return;
                }

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Map<String, Object> User = new HashMap<>();
                User.put("referal_link", invite_code);

                user_ref.child(userID).updateChildren(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            PersonInGroup person = new PersonInGroup(userID);
                            FirebaseDatabase.getInstance().getReference("Groups").child(invite_code).push()
                                    .setValue(person).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        dismiss();
                                    }
                                }

                            });
                        }

                    }
                });

            }
        });

        return view;
    }
}