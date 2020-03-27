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
import com.example.testapp.ClassObjects.Studies;
import com.example.testapp.ClassObjects.SubCategory;
import com.example.testapp.ClassObjects.SubStudies;

import java.util.ArrayList;
import java.util.List;

public class simple_list_item_2_filter extends ArrayAdapter{

    String ListName;
    String txtCat;
    String FilterPattern;
    private List FullSubList;
    private List FullParentList;

    SubCategory currSub;
    SubStudies currSub1;

    int categoryId = 0;
    int studiesId = 0;

    public simple_list_item_2_filter(Activity context, ArrayList subList, ArrayList ParentList, String listName) {
        super(context, 0, subList);
        FullSubList = new ArrayList<>(subList);
        FullParentList = new ArrayList<>(ParentList);
        ListName = listName;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return SubParentFilter;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if (ListName.equals("SubCategories")) {
            currSub = (SubCategory) getItem(position);
        }

        if (ListName.equals("SubStudies")){
            currSub1 = (SubStudies)getItem(position);
        }

        View listSub = convertView;
        if (listSub == null) {
            listSub = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView text1 = listSub.findViewById(android.R.id.text1);
        TextView text2 = listSub.findViewById(android.R.id.text2);

        if (ListName.equals("SubCategories")){
            text1.setText(currSub.getSubCategory());

            for (Category item : (ArrayList<Category>)FullParentList) {
                if (item.getCategoryId() == currSub.getCategoryId()) {
                    text2.setText( item.getCategory());
                    break;
                }
            }

        }else if (ListName.equals("SubStudies")){
            text1.setText(currSub1.getSubStudy());

            for (Studies item : (ArrayList<Studies>)FullParentList) {
                if (item.getStudyId() == currSub1.getStudyId()) {
                    text2.setText( item.getStudy());
                    break;
                }
            }
        }

        return listSub;
    }


    public void refreshList(SubCategory s) {
        this.FullSubList.add(s);
    }

    public void refreshList(SubStudies s) {
        this.FullSubList.add(s);
    }

    public void setCategoryId(int i) {
        this.categoryId = i;
    }

    public void setStudiesId(int i) {
        this.studiesId = i;
    }

    private Filter SubParentFilter = new Filter() {
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List suggestions = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                FilterPattern = "";
            } else {
                FilterPattern = constraint.toString().toLowerCase().trim();
            }
            if (ListName.equals("SubCategories")) {
                for (SubCategory item : (ArrayList<SubCategory>)FullSubList) {
                    if (constraint != null || constraint.length() != 0) {
                        if (item.getSubCategory().toLowerCase().contains(FilterPattern)) {
                            if (categoryId == item.getCategoryId() || categoryId == 0) {
                                suggestions.add(item);
                            }
                        }
                    } else if (categoryId == item.getCategoryId() || categoryId == 0) {
                        suggestions.add(item);
                    }
                }
            }else if (ListName.equals("SubStudies")){
                for (SubStudies item : (ArrayList<SubStudies>)FullSubList) {
                    if (constraint != null || constraint.length() != 0) {
                        if (item.getSubStudy().toLowerCase().contains(FilterPattern)) {
                            if (studiesId == item.getStudyId() || studiesId == 0) {
                                suggestions.add(item);
                            }
                        }
                    } else if (studiesId == item.getStudyId() || studiesId == 0) {
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
        }
    };
}
