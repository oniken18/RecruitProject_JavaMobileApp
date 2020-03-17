package com.example.testapp.ClassObjects;

public class SubCategory {

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
}
