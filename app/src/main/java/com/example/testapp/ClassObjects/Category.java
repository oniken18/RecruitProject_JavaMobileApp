package com.example.testapp.ClassObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Category implements Comparable{

    private int CategoryId;
    private String Category;

    public Category(int categoryId, String category) {
        CategoryId = categoryId;
        Category = category;
    }
    public Category() {
        CategoryId = 0;
        Category = "noName";
            }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    //implements Comparable
    @Override
    public int compareTo(Object st) {
        return this.CategoryId-((Category)st).getCategoryId();
    }

    public static void SortA_Z(ArrayList<Category> AL, Boolean isReverse) {
        if (isReverse) {
            Collections.sort(AL, new Comparator<Category>() {
                @Override
                public int compare(Category o1, Category o2) {
                    return o1.getCategory().toLowerCase().compareTo(o2.getCategory().toLowerCase());
                }
            });
        }else {
            Collections.sort(AL, new Comparator<Category>() {
                @Override
                public int compare(Category o1, Category o2) {
                    return o2.getCategory().toLowerCase().compareTo(o1.getCategory().toLowerCase());
                }
            });
        }
    }
}
