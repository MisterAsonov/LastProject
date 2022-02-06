package com.example.lastproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class teacher_activitie_adapter extends RecyclerView.Adapter<teacher_activitie_adapter.ViewHolder> {
    ArrayList<Activitie> activitie_list;
    Context context;
    ArrayList<String> keys;

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;

    }

    public teacher_activitie_adapter(ArrayList<Activitie> activitie_list, Context context) {
        this.activitie_list = activitie_list;
        this.context = context;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {
        TextView type,name,when;

        public ViewHolder (View view) {
            super(view);
            type = (TextView) view.findViewById(R.id.teacher_type_of_activitie);
            name = (TextView) view.findViewById(R.id.teacher_nem_of_activitie);
            when = (TextView) view.findViewById(R.id.when);

        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_teacher_activities_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int g) {
        int i = viewHolder.getAdapterPosition();
        Activitie tmp = activitie_list.get(i);
        viewHolder.type.setText(String.valueOf(tmp.getType()));
        viewHolder.name.setText(String.valueOf(tmp.getName()));
        viewHolder.when.setText(String.valueOf(tmp.getWhen()));

        /**
         * viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
         *             @Override
         *             public void onClick(View v) {
         *                 Intent intent = new Intent(context, teacher_Student_Profile.class);
         *                 intent.putExtra("tv_name", tmp.getName());
         *                 intent.putExtra("tv_lname", tmp.getLastname());
         *                 intent.putExtra("tv_email", tmp.getEmail());
         *                 intent.putExtra("tv_who", tmp.getWho());
         *                 intent.putExtra("tv_id", tmp.getUID());
         *                 intent.putExtra("key", keys.get(i));
         *
         *                 context.startActivity(intent);
         *
         *             }
         *         });
         */

    }

    public int getItemCount() {
        return activitie_list.size();
    }
}
