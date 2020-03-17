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

import com.example.testapp.ClassObjects.Capacity;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class CapacityLines extends ArrayAdapter<Capacity> {

    private String FilterPattern;
    private List<Capacity> FullCapacitiesList;

    public CapacityLines(Activity context, ArrayList<Capacity> Capacities) {
        super(context, 0, Capacities);
        FullCapacitiesList = new ArrayList<>(Capacities);
    }

    @NonNull
    @Override
    public Filter getFilter() { return CapacitiesFilter; }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listCapacities = convertView;
        if (listCapacities== null){
            listCapacities = LayoutInflater.from(getContext()).inflate(R.layout.capacities_list,parent,false);
        }

        TextView Name =  listCapacities.findViewById(R.id.Capacity);

        Capacity CurrCapacity = getItem(position);
        if (CurrCapacity !=null){
            Name.setText(CurrCapacity.getJobCapacity());
        }

        return listCapacities;
    }

    private Filter CapacitiesFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<Capacity> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(FullCapacitiesList);
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
                for (Capacity item : FullCapacitiesList) {
                    if (item.getJobCapacity().toLowerCase().contains(FilterPattern)) {
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
            return ((Capacity)resultValue).getJobCapacity();
        }
    };
}
