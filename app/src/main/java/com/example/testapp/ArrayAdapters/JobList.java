package com.example.testapp.ArrayAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testapp.ClassObjects.Job;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class JobList extends ArrayAdapter<Job> {

    private String FilterPattern;
    private List<Job> FullJobsList;
    private boolean IsActive;


    public JobList(Activity context, ArrayList<Job> Jobs) {
        super(context, 0, Jobs);
        FullJobsList = new ArrayList<>(Jobs);
    }

    @NonNull
    @Override
    public Filter getFilter() { return JobsFilter; }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listJobs = convertView;
        if (listJobs== null){
            listJobs = LayoutInflater.from(getContext()).inflate(R.layout.jobs_list,parent,false);
        }

        TextView txtJob =  listJobs.findViewById(R.id.txtJob);

        Job CurrJob = getItem(position);
        if (CurrJob !=null){
            System.out.println(CurrJob.getJob());
            txtJob.setText(CurrJob.getJob());
        }
        return listJobs;
    }

    private Filter JobsFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Job> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(FullJobsList);
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
                for (Job item : FullJobsList) {
                    System.out.println(item.getJob());
                    if (item.getJob().toLowerCase().contains(FilterPattern)) {
                        suggestions.add(item);
                    }
                }
            }

            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((Job)resultValue).getJob();
        }
    };

    public void setActive(boolean active) {
        IsActive = active;
    }
    public boolean isActive() {
        return IsActive;
    }
}
