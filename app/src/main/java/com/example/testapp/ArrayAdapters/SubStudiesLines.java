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

import com.example.testapp.ClassObjects.SubStudies;
import com.example.testapp.ClassObjects.SubStudies;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class SubStudiesLines extends ArrayAdapter<SubStudies> {

    private int StudiesId;
    private String FilterPattern;
    private List<SubStudies> FullSubStudiesList;


    public void setStudiesId(int studiesId) {
        StudiesId = studiesId;
    }

    public SubStudiesLines(Activity context, ArrayList<SubStudies> SubStudies) {
        super(context, 0, SubStudies);
        FullSubStudiesList = new ArrayList<>(SubStudies);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return SubStudiesFilter;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listSubStudies = convertView;
        if (listSubStudies == null) {
            listSubStudies = LayoutInflater.from(getContext()).inflate(R.layout.substudies_list, parent, false);
        }

        TextView Name = listSubStudies.findViewById(R.id.txtSubStudies);

        SubStudies CurrSubStudies = getItem(position);
        if (CurrSubStudies != null) {
            Name.setText(CurrSubStudies.getSubStudy());
        }

        return listSubStudies;
    }

    private Filter SubStudiesFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<SubStudies> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                for (SubStudies item : FullSubStudiesList) {
                    if (item.getStudyId() == StudiesId) {
                        suggestions.add(item);
                    }
                }
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
                for (SubStudies item : FullSubStudiesList) {
                    if ((item.getSubStudy().toLowerCase().contains(FilterPattern)) && item.getStudyId() == StudiesId) {
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
            return ((SubStudies) resultValue).getSubStudy();
        }
    };
}

