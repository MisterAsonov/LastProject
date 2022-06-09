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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class teacher_activitie_adapter extends RecyclerView.Adapter<teacher_activitie_adapter.ViewHolder>{

    ArrayList<Activitie> activities;
    Context context;
    ArrayList<String> keys;


    /**
     * מחלקה מקבלת Key
     * של הטיול
     * @param keys
     */
    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
        notifyDataSetChanged();
    }

    public teacher_activitie_adapter(ArrayList<Activitie> activities, Context context) {
        this.activities = activities;
        this.context = context;

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
        viewHolder.number_of_people.setText(String.valueOf((tmp.getEvent_participants()).size()));

        if(!String.valueOf(tmp.getImageUrl()).equals("")){
            Picasso.get()
                    .load(String.valueOf(tmp.getImageUrl()))
                    .fit()
                    .centerCrop()
                    .into(viewHolder.image);}

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שמפנה למסך עם פרטים של טיול
             */
            public void onClick(View v) {

                Intent intent = new Intent(context, event_details.class);

                intent.putExtra("event_titel", String.valueOf(tmp.getEvent_title()));
                intent.putExtra("event_location", String.valueOf(tmp.getEvent_location()));
                intent.putExtra("event_date", String.valueOf(tmp.getEvent_date()));
                intent.putExtra("event_time", String.valueOf(tmp.getEvent_time()));
                intent.putExtra("event_desc", String.valueOf(tmp.getEvent_desc()));
                intent.putExtra("event_imageUrl", String.valueOf(tmp.getImageUrl()));
                intent.putStringArrayListExtra("event_participants", tmp.getEvent_participants());
                intent.putExtra("event_key", String.valueOf(keys.get(i)));

                context.startActivity(intent);

            }
        });

    }

    /**
     * מחלקה שמחזירה את המספר הטיולים
     * @return
     */
    @Override
    public int getItemCount() {
        return activities.size();
    }


}
