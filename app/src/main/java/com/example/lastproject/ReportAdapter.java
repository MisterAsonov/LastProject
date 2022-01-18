package com.example.lastproject;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    ArrayList<Report> reports;
    Context context;
    private FirebaseAuth mAuth;
    private String userID;

    public ReportAdapter(ArrayList<Report> reports, Context context) {
        this.reports = reports;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView title;
         TextView explanation;
         TextView status;
         TextView date;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.NameOfProblem);
            explanation = (TextView) view.findViewById(R.id.explanationOfProblem);
            status = (TextView) view.findViewById(R.id.status);
            date = (TextView) view.findViewById(R.id.dateOfProblem);

        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.reports_list, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Report tmp = reports.get(i);
        viewHolder.title.setText(String.valueOf(tmp.getTitle()));
        viewHolder.explanation.setText(String.valueOf(tmp.getExplanation()));
        viewHolder.status.setText(String.valueOf(tmp.getStatus()));
        viewHolder.date.setText(String.valueOf(tmp.getDate()));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userID.equals(tmp.getCreator_id())) {
                    Intent intent = new Intent(context, report_for_student.class);
                    intent.putExtra("report_title", tmp.getTitle());
                    intent.putExtra("report_exp", tmp.getExplanation());
                    intent.putExtra("report_status", tmp.getStatus());
                    intent.putExtra("report_building", tmp.getBuilding());
                    intent.putExtra("report_room", tmp.getRoom());
                    intent.putExtra("report_date", tmp.getDate());
                    intent.putExtra("report_creator_id", tmp.getCreator_id());

                    context.startActivity(intent);
                }else{

                    Intent intent = new Intent(context, report_for_teacher.class);
                    intent.putExtra("report_title", tmp.getTitle());
                    intent.putExtra("report_exp", tmp.getExplanation());
                    intent.putExtra("report_status", tmp.getStatus());
                    intent.putExtra("report_building", tmp.getBuilding());
                    intent.putExtra("report_room", tmp.getRoom());
                    intent.putExtra("report_date", tmp.getDate());
                    intent.putExtra("report_creator_id", tmp.getCreator_id());

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }
}
