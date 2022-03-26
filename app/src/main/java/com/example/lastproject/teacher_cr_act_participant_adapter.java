package com.example.lastproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class teacher_cr_act_participant_adapter extends RecyclerView.Adapter<teacher_cr_act_participant_adapter.ViewHolder>{
    ArrayList<String> id;
    ArrayList<String> keys;
    Context context;

    DatabaseReference student;

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
        notifyDataSetChanged();
    }

    public teacher_cr_act_participant_adapter(ArrayList<String> id, Context context) {
        this.id = id;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.part_photo);

        }

    }

    @Override
    public teacher_cr_act_participant_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.participants_list, viewGroup, false);

        /**
         *  mAuth = FirebaseAuth.getInstance();
         *         FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         *         userID = user.getUid();
         */

        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(teacher_cr_act_participant_adapter.ViewHolder viewHolder, int g) {
        int i = viewHolder.getAdapterPosition();

        student = FirebaseDatabase.getInstance().
                getReference("Users");

        student.child(id.get(i)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User p = snapshot.getValue(User.class);
                String link = p.getmImageUrl();

                if(!link.equals(""))
                    Picasso.get().load(link).into(viewHolder.image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return id.size();
    }
}
