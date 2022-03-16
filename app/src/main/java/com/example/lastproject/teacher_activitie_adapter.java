package com.example.lastproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class teacher_activitie_adapter extends RecyclerView.Adapter<teacher_activitie_adapter.ViewHolder>{

    ArrayList<Activitie> activities;
    Context context;
    ArrayList<String> keys;

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
        notifyDataSetChanged();
    }

    public teacher_activitie_adapter(ArrayList<Activitie> activities, Context context, ArrayList<String> keys) {
        this.activities = activities;
        this.context = context;
        this.keys = keys;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {
        TextView title, date, number_of_people;
        ImageFilterView image;

        public ViewHolder (View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_of_event_list);
            date = (TextView) view.findViewById(R.id.date_event_list);
            number_of_people = (TextView) view.findViewById(R.id.number_of_parcitipance);
            image = (ImageFilterView) view.findViewById(R.id.image_of_event_list);

        }

    }

    public teacher_activitie_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_teacher_activities_list, viewGroup, false);

        return new teacher_activitie_adapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int i = viewHolder.getAdapterPosition();
        Activitie tmp = activities.get(i);

        viewHolder.title.setText(String.valueOf(tmp.getEvent_title()));
        viewHolder.date.setText(String.valueOf(tmp.getEvent_date()));
        viewHolder.number_of_people.setText((tmp.getEvent_participants()).size());

        if(!String.valueOf(tmp.getImageUrl()).equals("")){
            Picasso.get()
                    .load(String.valueOf(tmp.getImageUrl()))
                    .fit()
                    .centerCrop()
                    .into(viewHolder.image);}

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, teacher_Student_Profile.class);
                intent.putExtra("key", keys.get(i));

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return activities.size();
    }


}
