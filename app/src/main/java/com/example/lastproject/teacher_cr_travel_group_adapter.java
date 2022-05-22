package com.example.lastproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class teacher_cr_travel_group_adapter extends RecyclerView.Adapter<teacher_cr_travel_group_adapter.ViewHolder>{

    ArrayList<User> users;
    Context context;
    ArrayList<String> keys;

    boolean isSelected = false;
    ArrayList<String> id_of_participant = new ArrayList<>();


    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
        notifyDataSetChanged();
    }

    public teacher_cr_travel_group_adapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {
        String UID_of_participant;
        TextView name,lastName,who;
        CircleImageView photo, isSelected;
        ImageButton back,clear,save;

        public ViewHolder (View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.Name);
            lastName = (TextView) view.findViewById(R.id.LastName);
            who = (TextView) view.findViewById(R.id.who);
            photo = (CircleImageView) view.findViewById(R.id.profile_icon);
            isSelected = (CircleImageView) view.findViewById(R.id.isSelected);

            back = (ImageButton) view.findViewById(R.id.arrrow_btn);
            clear = (ImageButton) view.findViewById(R.id.clear_btn);
            save = (ImageButton) view.findViewById(R.id.save_btn);

        }

    }

    public teacher_cr_travel_group_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.students_list, viewGroup, false);

        return new teacher_cr_travel_group_adapter.ViewHolder(view);

    }

    public void onBindViewHolder(@NonNull teacher_cr_travel_group_adapter.ViewHolder viewHolder, int g) {
        int i = viewHolder.getAdapterPosition();
        User tmp = users.get(i);
        viewHolder.UID_of_participant = String.valueOf(tmp.getUID());
        viewHolder.name.setText(String.valueOf(tmp.getName()));
        viewHolder.lastName.setText(String.valueOf(tmp.getLastname()));
        viewHolder.who.setText(String.valueOf(tmp.getWho()));



        if(!String.valueOf(tmp.getmImageUrl()).equals("")){
            Picasso.get()
                    .load(String.valueOf(tmp.getmImageUrl()))
                    .fit()
                    .centerCrop()
                    .into(viewHolder.photo);}

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                isSelected = true;

                if(id_of_participant.contains(viewHolder.UID_of_participant)){
                    viewHolder.isSelected.setVisibility(View.INVISIBLE);
                    id_of_participant.remove(viewHolder.UID_of_participant);
                    //viewHolder.clear.setVisibility(View.INVISIBLE);
                    //viewHolder.save.setVisibility(View.INVISIBLE);

                } else {
                    viewHolder.isSelected.setVisibility(View.VISIBLE);
                    id_of_participant.add(viewHolder.UID_of_participant);
                    //viewHolder.save.setVisibility(View.VISIBLE);
                    //viewHolder.clear.setVisibility(View.VISIBLE);
                    //viewHolder.back.setVisibility(View.INVISIBLE);
                }
                if(id_of_participant.size() == 0)
                    isSelected = false;

                return true;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected){
                    if(id_of_participant.contains(viewHolder.UID_of_participant)) {
                        viewHolder.isSelected.setVisibility(View.INVISIBLE);
                        id_of_participant.remove(viewHolder.UID_of_participant);
                    }else {
                        viewHolder.isSelected.setVisibility(View.VISIBLE);
                        id_of_participant.add(viewHolder.UID_of_participant);
                    }
                    if(id_of_participant.size() == 0)
                        isSelected = false;
                }
            }
        });

    }

    public int getItemCount() {
        return users.size();
    }

}
