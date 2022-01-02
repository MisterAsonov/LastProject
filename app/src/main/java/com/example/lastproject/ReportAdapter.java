package com.example.lastproject;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    ArrayList<Report> reports;
    Context context;

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
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Report tmp = reports.get(i);
        viewHolder.title.setText(String.valueOf(tmp.getTitle()));
        viewHolder.explanation.setText(String.valueOf(tmp.getExplanation()));
        viewHolder.status.setText(String.valueOf(tmp.getStatus()));
        viewHolder.date.setText(String.valueOf(tmp.getDate()));
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }
}
