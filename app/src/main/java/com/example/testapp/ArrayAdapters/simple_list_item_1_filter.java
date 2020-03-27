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

import com.example.testapp.ClassObjects.StudiesType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class simple_list_item_1_filter extends ArrayAdapter<String> {
    
    String FilterPattern;
    private List<String> FullMyList;

    public simple_list_item_1_filter(Activity context, ArrayList<String> MyList) {
        super(context, 0, MyList);
        FullMyList = new ArrayList<String>(MyList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return SubCatFilter;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String currMyItem = getItem(position);

        View listMyList = convertView;
        if (listMyList == null) {
            listMyList = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        TextView text1 = listMyList.findViewById(android.R.id.text1);
        text1.setText(currMyItem);

        return listMyList;
    }

    public void refreshList(String newCity){
        this.FullMyList.add(newCity);
    }

    private Filter SubCatFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<String> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                FilterPattern = "";
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
            }

            for ( String item : FullMyList) {
                if (constraint != null || constraint.length() != 0) {
                    if (item.toLowerCase().contains(FilterPattern)) {
                        suggestions.add(item);
                    }
                } else {
                    suggestions.add(item);
                }
            }
            results.values=suggestions;
            results.count=suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
        }
    };
}
