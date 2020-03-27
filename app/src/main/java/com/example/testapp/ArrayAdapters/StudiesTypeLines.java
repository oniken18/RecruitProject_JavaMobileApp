package com.example.testapp.ArrayAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testapp.ClassObjects.StudiesType;
import com.example.testapp.ClassObjects.StudiesType;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class StudiesTypeLines extends ArrayAdapter<StudiesType> {
    private String FilterPattern;
    private List<StudiesType> FullStudiesTypeList;

    private int CId;

    public StudiesTypeLines(Activity context, ArrayList<StudiesType> Cities) {
        super(context, 0, Cities);
        FullStudiesTypeList = new ArrayList<>(Cities);
    }

    @NonNull
    @Override
    public Filter getFilter() { return StudiesTypeFilter; }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listStudiesType = convertView;
        if (listStudiesType== null){
            listStudiesType = LayoutInflater.from(getContext()).inflate(R.layout.studiestype_list,parent,false);
        }

        TextView Name =  listStudiesType.findViewById(R.id.StudiesType);

        StudiesType CurrStudiesType = getItem(position);
        if (CurrStudiesType !=null){
            Name.setText(CurrStudiesType.getStudyType());
        }

        return listStudiesType;
    }

    private Filter StudiesTypeFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<StudiesType> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(FullStudiesTypeList);
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
                for (StudiesType item : FullStudiesTypeList) {
                    if (item.getStudyType().toLowerCase().contains(FilterPattern)) {
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
            return ((StudiesType) resultValue).getStudyType();
        }
    };

    public Adapter getAdapter(){
        return getAdapter();
    }
}
