package com.example.lastproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.ViewHolder> {

    ArrayList<User> users;
    Context context;
    ArrayList<String> keys;

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
        notifyDataSetChanged();
    }

    public MyGroupAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {
        TextView name,lastName,who;
        CircleImageView photo;

        public ViewHolder (View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.Name);
            lastName = (TextView) view.findViewById(R.id.LastName);
            who = (TextView) view.findViewById(R.id.who);
            photo = (CircleImageView) view.findViewById(R.id.profile_icon);

        }

    }

    public ViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.students_list, viewGroup, false);

        return new ViewHolder (view);

    }

    public void onBindViewHolder(@NonNull ViewHolder  viewHolder, int g) {
        int i = viewHolder.getAdapterPosition();
        User tmp = users.get(i);
        viewHolder.name.setText(String.valueOf(tmp.getName()));
        viewHolder.lastName.setText(String.valueOf(tmp.getLastname()));
        viewHolder.who.setText(String.valueOf(tmp.getWho()));

        if(!String.valueOf(tmp.getmImageUrl()).equals("")){
        Picasso.get()
                .load(String.valueOf(tmp.getmImageUrl()))
                .fit()
                .centerCrop()
                .into(viewHolder.photo);}

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, teacher_Student_Profile.class);
                intent.putExtra("tv_name", tmp.getName());
                intent.putExtra("tv_lname", tmp.getLastname());
                intent.putExtra("tv_email", tmp.getEmail());
                intent.putExtra("tv_who", tmp.getWho());
                intent.putExtra("tv_id", tmp.getUID());
                intent.putExtra("url_photo", tmp.getmImageUrl());
                intent.putExtra("key", keys.get(i));

                context.startActivity(intent);

            }
        });
    }
    public int getItemCount() {
        return users.size();
    }
}
