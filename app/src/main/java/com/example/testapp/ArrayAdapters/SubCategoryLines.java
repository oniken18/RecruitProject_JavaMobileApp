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

import com.example.testapp.ClassObjects.SubCategory;
import com.example.testapp.R;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryLines extends ArrayAdapter<SubCategory> {

    private String FilterPattern;
    private List<SubCategory> FullCategoriesList;

    public SubCategoryLines(Activity context, ArrayList<SubCategory> Categories) {
        super(context, 0, Categories);
        FullCategoriesList = new ArrayList<>(Categories);
    }

    @NonNull
    @Override
    public Filter getFilter() { return CategoriesFilter; }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listCategories = convertView;
        if (listCategories== null){
            listCategories = LayoutInflater.from(getContext()).inflate(R.layout.subcategories_list,parent,false);
        }

        TextView Name =  listCategories.findViewById(R.id.txtSubCategory);

        SubCategory CurrSubCategory = getItem(position);
        if (CurrSubCategory !=null){
            Name.setText(CurrSubCategory.getSubCategory());
        }

        return listCategories;
    }

    private Filter CategoriesFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<SubCategory> suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                suggestions.addAll(FullCategoriesList);
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
                for (SubCategory item : FullCategoriesList) {
                    if (item.getSubCategory().toLowerCase().contains(FilterPattern)) {
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
            return ((SubCategory)resultValue).getSubCategory();
        }
    };
}
