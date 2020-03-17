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

import com.example.testapp.ClassObjects.Category;
import com.example.testapp.ClassObjects.Job;
import com.example.testapp.ClassObjects.SubCategory;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class JobLines extends ArrayAdapter<Job> {

    private String FilterPattern;
    private List<Job> FullJobsList;
    private boolean IsActive;
    private ArrayList<SubCategory> SubCategories;
    private ArrayList<Category> Categories;


    public JobLines(Activity context, ArrayList<Job> Jobs, ArrayList<Category> categories , ArrayList<SubCategory> subCategories) {
        super(context, 0, Jobs);
        FullJobsList = new ArrayList<>(Jobs);
        Categories = categories;
        SubCategories = subCategories;
    }

    @NonNull
    @Override
    public Filter getFilter() { return JobsFilter; }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listJobs = convertView;
        if (listJobs== null){
            listJobs = LayoutInflater.from(getContext()).inflate(R.layout.job_lines,parent,false);
        }

        TextView txtJob =  listJobs.findViewById(R.id.txtJob);
        TextView txtCategory =  listJobs.findViewById(R.id.txtCategory);
        TextView txtSubCategory =  listJobs.findViewById(R.id.txtSubCategory);

        Job CurrJob = getItem(position);
        if (CurrJob !=null){
            txtJob.setText(CurrJob.getJob());

            for (Category item : Categories) {
                if (item.getCategoryId() == (CurrJob.getCategoryId())) {
                    txtCategory.setText(item.getCategory());
                    break;
                }
            }
            for (SubCategory item : SubCategories) {
                if (item.getSubCategoryId() == (CurrJob.getSubCategoryId())) {
                    txtSubCategory.setText(item.getSubCategory());
                    break;
                }
            }
        }
        return listJobs;
    }

    private Filter JobsFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Job> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                FilterPattern="";
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
            }

            for (Job item : FullJobsList) {
                if (!IsActive || (IsActive && item.getIsActive())) {
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
