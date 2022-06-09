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

    public interface teacher_ivents {

        void update_selections(int count);


    }

    teacher_ivents ivents;
    ArrayList<User> users;
    Context context;
    ArrayList<String> keys;

    boolean isSelected = false;
    ArrayList<String> id_of_participant = new ArrayList<>();

    /**
     * מחלקה שמקבלת את הkey
     * ל המשתתפ בטיול
     * @param keys
     */
    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
        notifyDataSetChanged();
    }

    /**
     * מחלקה שמגדירה מי זה משתתפ בטיול
     * @param users
     * @param context
     * @param ids
     * @param ivents
     */
    public teacher_cr_travel_group_adapter(ArrayList<User> users, Context context, ArrayList<String> ids,teacher_ivents ivents) {
        this.id_of_participant = ids;
        this.ivents = ivents;
        this.users = users;
        this.context = context;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder  {
        String UID_of_participant;
        TextView name,lastName,who;
        CircleImageView photo, isSelected;


        public ViewHolder (View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.Name);
            lastName = (TextView) view.findViewById(R.id.LastName);
            who = (TextView) view.findViewById(R.id.who);
            photo = (CircleImageView) view.findViewById(R.id.profile_icon);
            isSelected = (CircleImageView) view.findViewById(R.id.isSelected);

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

        if(!id_of_participant.contains(viewHolder.UID_of_participant)){
            viewHolder.isSelected.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.isSelected.setVisibility(View.VISIBLE);
        }

        if(!String.valueOf(tmp.getmImageUrl()).equals("")){
            Picasso.get()
                    .load(String.valueOf(tmp.getmImageUrl()))
                    .fit()
                    .centerCrop()
                    .into(viewHolder.photo);}

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            /**
             * מחלקה שמחזהרה true כאשר היתה לחיצה ארוכה על משתתף ומסיפה אותו לרשימת המשתתפים בטיול אחרת מסירה אותו מרשימה ומחזירה false
             * @param view
             * @return
             */
            @Override
            public boolean onLongClick(View view) {

                isSelected = true;

                if(id_of_participant.contains(viewHolder.UID_of_participant)){
                    viewHolder.isSelected.setVisibility(View.INVISIBLE);
                    id_of_participant.remove(viewHolder.UID_of_participant);




                } else {
                    viewHolder.isSelected.setVisibility(View.VISIBLE);
                    id_of_participant.add(viewHolder.UID_of_participant);


                }
                ivents.update_selections(id_of_participant.size());
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
                    ivents.update_selections(id_of_participant.size());
                    if(id_of_participant.size() == 0)
                        isSelected = false;
                }
            }
        });

    }

    /**
     * מחלקה מחזירה מספר משתתפים בטיול
     * @return
     */
    public int getItemCount() {
        return users.size();
    }

    /**
     * מחלקה מסירה כל המשתתפים בטיול
     */
    public void ClearSelections(){
        id_of_participant.clear();
        isSelected = false;
        notifyDataSetChanged();
        ivents.update_selections(0);

    }

    /**
     * מחלקה מחזירה רשימה חדשה אם משתתפים בטיול
     * @return
     */
    public ArrayList<String> getList(){
        return id_of_participant;
    }

}
