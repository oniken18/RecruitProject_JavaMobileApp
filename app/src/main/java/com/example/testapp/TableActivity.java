package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.testapp.ArrayAdapters.CategoryLines;
import com.example.testapp.ArrayAdapters.StudiesLines;
import com.example.testapp.ArrayAdapters.simple_list_item_1_filter;
import com.example.testapp.ArrayAdapters.simple_list_item_2_filter;
import com.example.testapp.ClassObjects.Capacity;
import com.example.testapp.ClassObjects.Category;
import com.example.testapp.ClassObjects.City;
import com.example.testapp.ClassObjects.Studies;
import com.example.testapp.ClassObjects.StudiesType;
import com.example.testapp.ClassObjects.SubCategory;
import com.example.testapp.ClassObjects.SubStudies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Collections;


public class TableActivity extends AppCompatActivity {
    FrameLayout CategoryFrame;
    AutoCompleteTextView txtCategory;
    Button butAdd;
    EditText txtSearch;
    ListView TableList;
    TextView txtTitle;

    ArrayList<StudiesType> studiesTypeList;
    ArrayList<Studies> studiesList;
    ArrayList<SubStudies> subStudiesList;
    ArrayList<City> cityList;
    ArrayList<Category> categoryList;
    ArrayList<SubCategory> subCategoryList;
    ArrayList<Capacity> CapacityList;

    ArrayList<String> strList = new ArrayList<>();

    ArrayList<SubStudies> fullSubStudiesList;
    ArrayList<Studies> fullStudiesList;

    ArrayList<SubCategory> fullSubCatList;
    ArrayList<Category> fullCatList;

    simple_list_item_1_filter adapter;
    simple_list_item_2_filter arrAdapter;
    ReadAndWriteToFile RW = new ReadAndWriteToFile();

    SubCategory newSubCategory;
    SubStudies newSubStudies;
    private Category selectedCategory = new Category();
    private Studies selectedStudies = new Studies();
    String TableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent = getIntent();
        TableName = intent.getStringExtra("tblName");

        txtTitle = findViewById(R.id.tblName);
        butAdd = findViewById(R.id.butAdd);
        txtSearch = findViewById(R.id.txtSearch);
        TableList = findViewById(R.id.TableList);
        txtCategory = findViewById(R.id.txtCategory);
        CategoryFrame = findViewById(R.id.CategoryFrame);

        ViewGroup.LayoutParams lp = CategoryFrame.getLayoutParams();

        switch (TableName) {

            case "StudiesTypeList":
                txtTitle.setText("Studies Types");
                lp.height = 0;
                studiesTypeList = new ArrayList<>();
                studiesTypeList = RW.ReadJsonFile(getApplicationContext(), "StudiesTypeList");
                StudiesType.SortA_Z(studiesTypeList,false);

                for (StudiesType item : studiesTypeList) {
                    strList.add(item.getStudyType());
                }

                break;

            case "StudiesList":
                txtTitle.setText("Studies");
                lp.height = 0;
                studiesList = new ArrayList<>();
                studiesList = RW.ReadJsonFile(getApplicationContext(), "StudiesList");
                Studies.SortA_Z(studiesList,false);

                for (Studies item : studiesList) {
                    strList.add(item.getStudy());
                }
                break;

            case "SubStudiesList":
                txtTitle.setText("Sub Studies");
                txtCategory.setVisibility(View.VISIBLE);
                subStudiesList = new ArrayList<>();
                subStudiesList = RW.ReadJsonFile(getApplicationContext(), "SubStudiesList");
                SubStudies.SortA_Z(subStudiesList,false);
                fullSubStudiesList = new ArrayList<>(subStudiesList);
                studiesList = new ArrayList<>();
                studiesList = RW.ReadJsonFile(getApplicationContext(), "StudiesList");
                Studies.SortA_Z(studiesList,false);
                fullStudiesList = new ArrayList<>(studiesList);

                StudiesLines StudiesAdapter = new StudiesLines(this, studiesList);
                txtCategory.setAdapter(StudiesAdapter);

                TableActivity.SelectedStudies SS = new TableActivity.SelectedStudies();
                txtCategory.setOnItemSelectedListener(SS);
                txtCategory.setOnItemClickListener(SS);
                txtCategory.setOnFocusChangeListener(SS);
                break;

            case "CityList":
                txtTitle.setText("Cities");
                lp.height = 0;
                cityList = new ArrayList<>();
                cityList = RW.ReadJsonFile(getApplicationContext(), "CityList");
                City.SortA_Z(cityList,false);
                for (City item : cityList) {
                    strList.add(item.getCity());
                }
                break;
            case "CapacityList":
                txtTitle.setText("Capcities");
                lp.height = 0;
                CapacityList = new ArrayList<>();
                CapacityList = RW.ReadJsonFile(getApplicationContext(), "CapacityList");
                Capacity.SortA_Z(CapacityList,false);
                for (Capacity item : CapacityList) {
                    strList.add(item.getJobCapacity());
                }
                break;
            case "CategoryList":
                txtTitle.setText("Categories");
                lp.height = 0;
                categoryList = new ArrayList<>();
                categoryList = RW.ReadJsonFile(getApplicationContext(), "CategoryList");
                Category.SortA_Z(categoryList,false);
                for (Category item : categoryList) {
                    strList.add(item.getCategory());
                }
                break;

            case "SubCategoryList":
                txtTitle.setText("Sub Categories");
                txtCategory.setVisibility(View.VISIBLE);

                subCategoryList = new ArrayList<>();
                subCategoryList = RW.ReadJsonFile(getApplicationContext(), "SubCategoryList");
                SubCategory.SortA_Z(subCategoryList,false);
                fullSubCatList = new ArrayList<>(subCategoryList);

                categoryList = new ArrayList<>();
                categoryList = RW.ReadJsonFile(getApplicationContext(), "CategoryList");
                Category.SortA_Z(categoryList,false);
                fullCatList = new ArrayList<>(categoryList);

                CategoryLines CategoryAdapter = new CategoryLines(this, categoryList);
                txtCategory.setAdapter(CategoryAdapter);

                TableActivity.SelectedCategory SJ = new TableActivity.SelectedCategory();
                txtCategory.setOnItemSelectedListener(SJ);
                txtCategory.setOnItemClickListener(SJ);
                txtCategory.setOnFocusChangeListener(SJ);
                break;
        }

        if (TableName.equals("SubCategoryList")) {
            arrAdapter = new simple_list_item_2_filter(this, subCategoryList, categoryList, "SubCategories");
            TableList.setAdapter(arrAdapter);
        } else if (TableName.equals("SubStudiesList")) {
            arrAdapter = new simple_list_item_2_filter(this, subStudiesList, studiesList, "SubStudies");
            TableList.setAdapter(arrAdapter);
        } else {
            adapter = new simple_list_item_1_filter(this, strList);
            TableList.setAdapter(adapter);
        }

        txtCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TableName.equals("SubCategoryList")) {
                    arrAdapter.setCategoryId(0);
                } else if (TableName.equals("SubStudiesList")) {
                    arrAdapter.setStudiesId(0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TableName.equals("SubCategoryList")) {
                    arrAdapter.getFilter().filter(s);
                    arrAdapter.notifyDataSetChanged();
                    arrAdapter.setCategoryId(0);
                } else if (TableName.equals("SubStudiesList")) {
                    arrAdapter.getFilter().filter(s);
                    arrAdapter.notifyDataSetChanged();
                    arrAdapter.setStudiesId(0);
                } else {
                    adapter.getFilter().filter(s);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        butAdd.setOnClickListener(new View.OnClickListener() {
            String TempAddition;
            Boolean isNoCat;
            String strToast = "";

            @Override
            public void onClick(View v) {
                isNoCat = false;
                if ((TableName.equals("SubCategoryList") && selectedCategory.getCategoryId() == 0) || (TableName.equals("SubStudiesList") && selectedStudies.getStudyId() == 0)) {
                    isNoCat = true;
                }

                if ((txtSearch.getText().toString().equals("") || txtSearch.getText() == null) || isNoCat) {

                    switch (TableName) {
                        case "CityList":
                            strToast = "fill city name";
                            break;
                        case "CapacityList":
                            strToast = "fill capacity";
                            break;
                        case "CategoryList":
                            strToast = "fill category";
                            break;
                        case "SubCategoryList":
                            if (isNoCat) {
                                strToast = "fill category";
                                break;
                            } else {
                                strToast = "fill sub category";
                                break;
                            }
                        case "StudiesTypeList":
                            strToast = "fill study type";
                            break;
                        case "StudiesList":
                            strToast = "fill studies";
                            break;
                        case "SubStudiesList":
                            if (isNoCat) {
                                strToast = "fill studies";
                                break;
                            } else {
                                strToast = "fill sub studies";
                                break;
                            }
                    }

                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtSearch.requestFocus();
                } else {
                    Boolean isFound = false;
                    switch (TableName) {
                        case "CityList":
                            for (City item : cityList) {
                                if (item.getCity().trim().toLowerCase().equals(txtSearch.getText().toString().toLowerCase().trim())) {
                                    isFound = true;
                                    break;
                                }
                            }
                            break;
                        case "CapacityList":
                            for (Capacity item : CapacityList) {
                                if (item.getJobCapacity().trim().toLowerCase().equals(txtSearch.getText().toString().toLowerCase().trim())) {
                                    isFound = true;
                                    break;
                                }
                            }
                            break;
                        case "CategoryList":
                            for (Category item : categoryList) {
                                if (item.getCategory().trim().toLowerCase().equals(txtSearch.getText().toString().toLowerCase().trim())) {
                                    isFound = true;
                                    break;
                                }
                            }
                            break;

                        case "SubCategoryList":
                            for (SubCategory item : subCategoryList) {
                                if (item.getSubCategory().trim().toLowerCase().equals(txtSearch.getText().toString().toLowerCase().trim())) {
                                    isFound = true;
                                    break;
                                }
                            }
                            break;

                        case "StudiesTypeList":
                            for (StudiesType item : studiesTypeList) {
                                if (item.getStudyType().trim().toLowerCase().equals(txtSearch.getText().toString().toLowerCase().trim())) {
                                    isFound = true;
                                    break;
                                }
                            }
                            break;
                        case "StudiesList":
                            for (Studies item : studiesList) {
                                if (item.getStudy().trim().toLowerCase().equals(txtSearch.getText().toString().toLowerCase().trim())) {
                                    isFound = true;
                                    break;
                                }
                            }
                            break;

                        case "SubStudiesList":
                            for (SubStudies item : subStudiesList) {
                                if (item.getSubStudy().trim().toLowerCase().equals(txtSearch.getText().toString().toLowerCase().trim())) {
                                    isFound = true;
                                    break;
                                }
                            }
                            break;
                    }

                    if (isFound) {
                        String strToast = "item already exist in table";
                        Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    } else {

                        Gson GB = new GsonBuilder().create();
                        String strJSON = "";
                        int ItemMaxId=1;
                        switch (TableName) {
                            case "CityList":
                                if (cityList.size()>0) {
                                    ItemMaxId = Collections.max(cityList).getCityId() + 1;
                                }
                                City newCity = new City(ItemMaxId, txtSearch.getText().toString(), 1);
                                cityList.add(newCity);
                                strJSON = GB.toJson(cityList);
                                TempAddition = newCity.getCity();
                                break;
                            case "CapacityList":
                                if (CapacityList.size()>0) {
                                    ItemMaxId = Collections.max(CapacityList).getJobCapacityId() + 1;
                                }
                                Capacity newCapacity = new Capacity(ItemMaxId, txtSearch.getText().toString());
                                CapacityList.add(newCapacity);
                                strJSON = GB.toJson(CapacityList);
                                TempAddition = newCapacity.getJobCapacity();
                                break;
                            case "CategoryList":
                                if (categoryList.size()>0) {
                                    ItemMaxId = Collections.max(categoryList).getCategoryId() + 1;
                                }
                                Category newCategory = new Category(ItemMaxId, txtSearch.getText().toString());
                                categoryList.add(newCategory);
                                strJSON = GB.toJson(categoryList);
                                TempAddition = newCategory.getCategory();
                                break;

                            case "SubCategoryList":
                                if (subCategoryList.size()>0) {
                                    ItemMaxId = Collections.max(subCategoryList).getSubCategoryId() + 1;
                                }
                                newSubCategory = new SubCategory(ItemMaxId, txtSearch.getText().toString(), selectedCategory.getCategoryId());
                                fullSubCatList.add(newSubCategory);
                                strJSON = GB.toJson(fullSubCatList);
                                break;

                            case "StudiesTypeList":
                                if (studiesTypeList.size()>0) {
                                    ItemMaxId = Collections.max(studiesTypeList).getStudyTypeID() + 1;
                                }
                                StudiesType newStudiesType = new StudiesType(ItemMaxId, txtSearch.getText().toString());
                                studiesTypeList.add(newStudiesType);
                                strJSON = GB.toJson(studiesTypeList);
                                TempAddition = newStudiesType.getStudyType();
                                break;

                            case "StudiesList":
                                if (studiesList.size()>0) {
                                    ItemMaxId = Collections.max(studiesList).getStudyId() + 1;
                                }
                                Studies newStudies = new Studies(ItemMaxId, txtSearch.getText().toString());
                                studiesList.add(newStudies);
                                strJSON = GB.toJson(studiesList);
                                TempAddition = newStudies.getStudy();
                                break;

                            case "SubStudiesList":
                                if (subStudiesList.size()>0) {
                                    ItemMaxId = Collections.max(subStudiesList).getSubStudyId() + 1;
                                }
                                newSubStudies = new SubStudies(ItemMaxId, txtSearch.getText().toString(), selectedStudies.getStudyId());
                                fullSubStudiesList.add(newSubStudies);
                                strJSON = GB.toJson(fullSubStudiesList);
                                break;
                        }

                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(strJSON);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RW.WriteToFile(getApplicationContext(), TableName, jsonArray);
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                        txtSearch.setText("");

                        if (TableName.equals("SubCategoryList")) {
                            arrAdapter.refreshList(newSubCategory);
                            arrAdapter.getFilter().filter("");
                            arrAdapter.notifyDataSetChanged();
                            txtCategory.setText("");
                        } else if (TableName.equals("SubStudiesList")) {
                            arrAdapter.refreshList(newSubStudies);
                            arrAdapter.getFilter().filter("");
                            arrAdapter.notifyDataSetChanged();
                            txtCategory.setText("");
                        } else {
                            adapter.refreshList(TempAddition);
                            adapter.getFilter().filter("");
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    //SELECT CATEGORY
    class SelectedCategory implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedCategory = (Category) txtCategory.getAdapter().getItem(position);
            arrAdapter.setCategoryId(selectedCategory.getCategoryId());
            arrAdapter.getFilter().filter(txtSearch.getText());
            arrAdapter.notifyDataSetChanged();
            txtSearch.requestFocus();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedCategory = (Category) txtCategory.getAdapter().getItem(position);
            arrAdapter.setCategoryId(selectedCategory.getCategoryId());
            arrAdapter.getFilter().filter(txtSearch.getText());
            arrAdapter.notifyDataSetChanged();
            txtSearch.requestFocus();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            txtCategory.setText("");
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (txtCategory.hasFocus()) {
                txtCategory.showDropDown();
            }
        }
    }

    //SELECT STUDIES
    class SelectedStudies implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedStudies = (Studies) txtCategory.getAdapter().getItem(position);
            arrAdapter.setCategoryId(selectedStudies.getStudyId());
            arrAdapter.getFilter().filter(txtSearch.getText());
            arrAdapter.notifyDataSetChanged();
            txtSearch.requestFocus();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedStudies = (Studies) txtCategory.getAdapter().getItem(position);
            arrAdapter.setCategoryId(selectedStudies.getStudyId());
            arrAdapter.getFilter().filter(txtSearch.getText());
            arrAdapter.notifyDataSetChanged();
            txtSearch.requestFocus();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            txtCategory.setText("");
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (txtCategory.hasFocus()) {
                txtCategory.showDropDown();
            }
        }
    }
}
