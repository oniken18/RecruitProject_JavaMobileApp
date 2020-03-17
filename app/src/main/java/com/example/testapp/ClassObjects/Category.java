package com.example.testapp.ClassObjects;

public class Category {

    private int CategoryId;
    private String Category;

    public Category(int categoryId, String category) {
        CategoryId = categoryId;
        Category = category;
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
}
