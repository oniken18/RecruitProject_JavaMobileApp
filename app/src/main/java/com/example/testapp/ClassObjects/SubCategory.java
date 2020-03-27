package com.example.testapp.ClassObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SubCategory implements Comparable{

    private int SubCategoryId;
    private String SubCategory;
    private int CategoryId;

    public SubCategory(int subCategoryId, String subCategory, int categoryId) {
        SubCategoryId = subCategoryId;
        SubCategory = subCategory;
        CategoryId = categoryId;
    }

    public int getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    public String getSubCategory() {
        return SubCategory;
    }

    public void setSubCategory(String subCategory) {
        SubCategory = subCategory;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    //implements Comparable
    @Override
    public int compareTo(Object st) {
        return this.SubCategoryId-((SubCategory)st).getSubCategoryId();
    }

    public static void SortA_Z(ArrayList<SubCategory> AL, Boolean isReverse) {
        if (isReverse) {
            Collections.sort(AL, new Comparator<SubCategory>() {
                @Override
                public int compare(SubCategory o1, SubCategory o2) {
                    return o1.getSubCategory().toLowerCase().compareTo(o2.getSubCategory().toLowerCase());
                }
            });
        }else {
            Collections.sort(AL, new Comparator<SubCategory>() {
                @Override
                public int compare(SubCategory o1, SubCategory o2) {
                    return o2.getSubCategory().toLowerCase().compareTo(o1.getSubCategory().toLowerCase());
                }
            });
        }
    }
}
