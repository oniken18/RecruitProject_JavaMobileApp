package com.example.testapp.ArrayAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testapp.ClassObjects.City;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;


public class CityLines extends ArrayAdapter<City> {

    private String FilterPattern;
    private List<City> FullCitiesList;

    private int CId;

    public CityLines(Activity context, ArrayList<City> Cities) {
        super(context, 0, Cities);
        FullCitiesList = new ArrayList<>(Cities);
    }

    @NonNull
    @Override
    public Filter getFilter() { return CitiesFilter; }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listCities = convertView;
        if (listCities== null){
            listCities = LayoutInflater.from(getContext()).inflate(R.layout.cities_list,parent,false);
        }

        TextView Name =  listCities.findViewById(R.id.CityName);

        City CurrCity = getItem(position);
        if (CurrCity !=null){
            Name.setText(CurrCity.getCity());
        }

        return listCities;
    }

    private Filter CitiesFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<City> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(FullCitiesList);
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
                for (City item : FullCitiesList) {
                    if (item.getCity().toLowerCase().contains(FilterPattern)) {
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
            return ((City) resultValue).getCity();
        }
    };

    public Adapter getAdapter(){
        return getAdapter();
    }
}


