package com.example.testapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testapp.ArrayAdapters.CapacityLines;
import com.example.testapp.ArrayAdapters.CategoryLines;
import com.example.testapp.ArrayAdapters.CityLines;
import com.example.testapp.ArrayAdapters.SubCategoryLines;
import com.example.testapp.ClassObjects.Capacity;
import com.example.testapp.ClassObjects.Category;
import com.example.testapp.ClassObjects.City;
import com.example.testapp.ClassObjects.Client;
import com.example.testapp.ClassObjects.Job;
import com.example.testapp.ClassObjects.SubCategory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.$Gson$Preconditions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class JobDetailsActivity extends AppCompatActivity {

    AutoCompleteTextView txtCity, txtJobCapacity, txtCategory, txtSubCategory;
    EditText txtJob, txtJobNumber;

    Switch sActive;
    TextView lblTitle;
    Button butSubmit;
    SubCategoryLines SubCategoryAdapter;

    ArrayList<City> Cities = new ArrayList<>();
    ArrayList<Job> Jobs = new ArrayList<>();
    ArrayList<Capacity> Capacities = new ArrayList<>();
    ArrayList<Category> Categories = new ArrayList<>();
    ArrayList<SubCategory> SubCategories = new ArrayList<>();

    Job NewJob;
    Boolean isChange = false;

    ReadAndWriteToFile RW = new ReadAndWriteToFile();

    int nmJob = 0;

    public void setFocus(View view) {
        view.requestFocus();
    }

    public void dropDown(AutoCompleteTextView view) {
        view.showDropDown();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        txtCity = findViewById(R.id.CityId);
        txtJob = findViewById(R.id.txtJob);
        txtJobCapacity = findViewById(R.id.JobCapacityId);
        txtJobNumber = findViewById(R.id.JobNumber);
        txtCategory = findViewById(R.id.Category);
        txtSubCategory = findViewById(R.id.SubCategory);
        sActive = findViewById(R.id.isActive);
        lblTitle = findViewById(R.id.lblTitle);
        butSubmit = findViewById(R.id.butSubmit);

        Cities = RW.ReadJsonFile(getApplicationContext(), "CityList");
        Jobs = RW.ReadJsonFile(getApplicationContext(), "JobList");
        Capacities = RW.ReadJsonFile(getApplicationContext(), "CapacityList");
        Categories = RW.ReadJsonFile(getApplicationContext(), "CategoryList");
        SubCategories = RW.ReadJsonFile(getApplicationContext(), "SubCategoryList");

        CapacityLines CapacityAdapter = new CapacityLines(this, Capacities);
        txtJobCapacity.setAdapter(CapacityAdapter);

        CityLines CityAdapter = new CityLines(this, Cities);
        txtCity.setAdapter(CityAdapter);

        CategoryLines CategoryAdapter = new CategoryLines(this, Categories);
        txtCategory.setAdapter(CategoryAdapter);

        SubCategoryAdapter = new SubCategoryLines(this, SubCategories);
        txtSubCategory.setAdapter(SubCategoryAdapter);

        SelectedCity SC = new SelectedCity();
        txtCity.setOnItemSelectedListener(SC);
        txtCity.setOnItemClickListener(SC);
        txtCity.setOnFocusChangeListener(SC);

        SelectedCapacity SCap = new SelectedCapacity();
        txtJobCapacity.setOnItemSelectedListener(SCap);
        txtJobCapacity.setOnItemClickListener(SCap);
        txtJobCapacity.setOnFocusChangeListener(SCap);

        SelectedCategory SJ = new SelectedCategory();
        txtCategory.setOnItemSelectedListener(SJ);
        txtCategory.setOnItemClickListener(SJ);
        txtCategory.setOnFocusChangeListener(SJ);

        SelectedSubCategory SSJ = new SelectedSubCategory();
        txtSubCategory.setOnItemSelectedListener(SSJ);
        txtSubCategory.setOnItemClickListener(SSJ);
        txtSubCategory.setOnFocusChangeListener(SSJ);

        try {
            nmJob = (int) getIntent().getSerializableExtra("nmJob");
        } catch (Exception e) {
            nmJob = 0;
        }

        if (nmJob == 0) {
            NewJob = new Job();
            txtJob.requestFocus();
        } else {
            for (Job CL : Jobs) {
                if (CL.getJobId() == nmJob) {
                    NewJob = CL;
                    lblTitle.setText("Job:" + " " + CL.getJobNumber());
                    break;
                }
            }
            sActive.setChecked(NewJob.getIsActive());
            txtJob.setText(NewJob.getJob());
            txtJobNumber.setText(Integer.toString(NewJob.getJobNumber()));

            for (int i = 0; i < Cities.size(); i++) {
                if (NewJob.getCityId() == Cities.get(i).getCityId()) {
                    txtCity.setText(Cities.get(i).getCity());
                    break;
                }
            }

            for (Category item : Categories) {
                if (item.getCategoryId() == (NewJob.getCategoryId())) {
                    txtCategory.setText(item.getCategory());
                    break;
                }
            }
            for (SubCategory item : SubCategories) {
                if (item.getSubCategoryId() == (NewJob.getSubCategoryId())) {
                    txtSubCategory.setText(item.getSubCategory());
                    break;
                }
            }

            for (int i = 0; i < Capacities.size(); i++) {
                if (NewJob.getCityId() == Capacities.get(i).getJobCapacityId()) {
                    txtJobCapacity.setText(Capacities.get(i).getJobCapacity());
                    break;
                }
            }
        }

        sActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NewJob.setIsActive(isChecked);
            }
        });

        txtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCity.showDropDown();
            }
        });

        txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCategory.showDropDown();
            }
        });

        txtSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSubCategory.showDropDown();
            }
        });

        txtJobCapacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtJobCapacity.showDropDown();
            }
        });

        txtCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                NewJob.setCategoryId(0);
//                txtSubCategory.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtSubCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                NewJob.setSubCategoryId(0);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        butSubmit.setOnClickListener(new View.OnClickListener() {
            private FileWriter file;

            @Override
            public void onClick(View v) {

                String strToast;

                if (txtJob.getText().toString().trim().equals("")) {
                    strToast = "Fill Job Description";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtJob.requestFocus();
                    return;
                }

                if (txtJobNumber.getText().toString().trim().equals("")) {
                    strToast = "Fill Last Name";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtJobNumber.requestFocus();
                    return;
                }

                if (NewJob.getCategoryId() == 0) {
                    strToast = "Fill Job Category";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtCategory.requestFocus();
                    return;
                }

                if (NewJob.getSubCategoryId() == 0) {
                    strToast = "Fill Job Sub Category";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtSubCategory.requestFocus();
                    return;
                }

                if (NewJob.getJobCapacityId() == 0) {
                    strToast = "Fill Job Capacity";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtJobCapacity.requestFocus();
                    return;
                }
                if (NewJob.getCityId() == 0) {
                    strToast = "Fill City";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtCity.requestFocus();
                    return;
                }
                if (Jobs.size()>0) {
                    NewJob.setJobId(Collections.max(Jobs).getJobId() + 1);
                }else{
                    NewJob.setJobId(1);
                }
                NewJob.setJob(txtJob.getText().toString());
                NewJob.setJobNumber(Integer.parseInt(txtJobNumber.getText().toString()));
                NewJob.setIsActive(sActive.isChecked());

                if (nmJob == 0) {
                    NewJob.setJobId(Jobs.size() + 1);
                    Jobs.add(NewJob);
                }

                Gson GB = new GsonBuilder().create();
                String strJSON = GB.toJson(Jobs);
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(strJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RW.WriteToFile(getApplicationContext(), "JobList", jsonArray);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(getApplicationContext(), JobsActivity.class);
                myIntent.putExtra("activity", "JobDetailsActivity");
                startActivity(myIntent);
            }
        });
    }

    //SELECT CITY
    class SelectedCity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        private City SelectedCity;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SelectedCity = (City) txtCity.getAdapter().getItem(position);
            NewJob.setCityId(SelectedCity.getCityId());
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectedCity = (City) txtCity.getAdapter().getItem(position);
            NewJob.setCityId(SelectedCity.getCityId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewJob.setCityId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (txtCity.hasFocus()) {
                dropDown(txtCity);
            }
        }
    }

    //SELECT CAPACITY
    class SelectedCapacity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        private Capacity SelectedCapacity;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SelectedCapacity = (Capacity) txtJobCapacity.getAdapter().getItem(position);
            NewJob.setJobCapacityId(SelectedCapacity.getJobCapacityId());
            setFocus(txtCity);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectedCapacity = (Capacity) txtJobCapacity.getAdapter().getItem(position);
            NewJob.setJobCapacityId(SelectedCapacity.getJobCapacityId());
            setFocus(txtCity);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewJob.setJobTypeId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (txtJobCapacity.hasFocus()) {
                dropDown(txtJobCapacity);
            }
        }
    }

    //SELECT CATEGORY
    class SelectedCategory implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        private Category SelectedCategory;

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SelectedCategory = (Category) txtCategory.getAdapter().getItem(position);
            NewJob.setCategoryId(SelectedCategory.getCategoryId());
            SubCategoryAdapter.setCategoryId(NewJob.getCategoryId());
            txtSubCategory.setAdapter(SubCategoryAdapter);
//            txtCategory.dismissDropDown();
//           txtSubCategory.setText("",true);
//            resetTxt();
            txtSubCategory.refreshAutoCompleteResults();
            setFocus(txtSubCategory);
        }
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectedCategory = (Category) txtCategory.getAdapter().getItem(position);
            NewJob.setCategoryId(SelectedCategory.getCategoryId());
            SubCategoryAdapter.setCategoryId(NewJob.getCategoryId());
            txtSubCategory.setAdapter(SubCategoryAdapter);
            txtSubCategory.refreshAutoCompleteResults();
//            txtCategory.dismissDropDown();
//            resetTxt();
            setFocus(txtSubCategory);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewJob.setJobTypeId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (txtCategory.hasFocus()) {
                dropDown(txtCategory);
            }
        }
    }

    //SELECT SUB CATEGORY
    class SelectedSubCategory implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        private SubCategory SelectedSubCategory;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SelectedSubCategory = (SubCategory) txtSubCategory.getAdapter().getItem(position);
            NewJob.setSubCategoryId(SelectedSubCategory.getSubCategoryId());
            setFocus(txtJobCapacity);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectedSubCategory = (SubCategory) txtSubCategory.getAdapter().getItem(position);
            NewJob.setSubCategoryId(SelectedSubCategory.getSubCategoryId());
            setFocus(txtJobCapacity);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewJob.setJobTypeId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (txtSubCategory.hasFocus()) {
//                dropDown(txtSubCategory);
            }
        }

    }
}
