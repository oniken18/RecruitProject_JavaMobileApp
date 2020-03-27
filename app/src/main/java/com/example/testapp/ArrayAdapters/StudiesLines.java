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
import com.example.testapp.ClassObjects.Studies;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class StudiesLines  extends ArrayAdapter<Studies> {
    private String FilterPattern;
    private List<Studies> FullStudiesList;

    public StudiesLines(Activity context, ArrayList<Studies> studies) {
        super(context, 0, studies);
        FullStudiesList = new ArrayList<>(studies);
    }

    @NonNull
    @Override
    public Filter getFilter() { return StudiesFilter; }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listStudies = convertView;
        if (listStudies== null){
            listStudies = LayoutInflater.from(getContext()).inflate(R.layout.studies_list,parent,false);
        }

        TextView Name =  listStudies.findViewById(R.id.Study);

        Studies CurrStudy = getItem(position);
        if (CurrStudy !=null){
            Name.setText(CurrStudy.getStudy());
        }

        return listStudies;
    }

    private Filter StudiesFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Studies> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(FullStudiesList);
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
                for (Studies item : FullStudiesList) {
                    if (item.getStudy().toLowerCase().contains(FilterPattern)) {
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
            return ((Studies)resultValue).getStudy();
        }
    };
}
