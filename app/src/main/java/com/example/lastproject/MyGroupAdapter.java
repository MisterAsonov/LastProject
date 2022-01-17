package com.example.lastproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyGroupAdapter extends RecyclerView.Adapter<MyGroupAdapter.ViewHolder> {

    ArrayList<User> users;
    Context context;

    public MyGroupAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {
        TextView name,lastName,who;

        public ViewHolder (View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.Name);
            lastName = (TextView) view.findViewById(R.id.LastName);
            who = (TextView) view.findViewById(R.id.who);

        }


    }


    public ViewHolder  onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.students_list, viewGroup, false);

        return new ViewHolder (view);

    }



    public void onBindViewHolder(@NonNull ViewHolder  viewHolder, int i) {
        User tmp = users.get(i);
        viewHolder.name.setText(String.valueOf(tmp.getName()));
        viewHolder.lastName.setText(String.valueOf(tmp.getLastname()));
        viewHolder.who.setText(String.valueOf(tmp.getWho()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, tmp.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public int getItemCount() {
        return users.size();
    }
}
