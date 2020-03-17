package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
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
import com.example.testapp.ArrayAdapters.CityLines;
import com.example.testapp.ArrayAdapters.JobLines;
import com.example.testapp.ArrayAdapters.JobList;
import com.example.testapp.ClassObjects.Capacity;
import com.example.testapp.ClassObjects.Category;
import com.example.testapp.ClassObjects.City;
import com.example.testapp.ClassObjects.Client;
import com.example.testapp.ClassObjects.Job;
import com.example.testapp.ClassObjects.SubCategory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.FileWriter;
import java.util.ArrayList;


import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;


public class ClientDetailsActivity extends AppCompatActivity {

    Client NewClient;
    Boolean isChange = false;

    ArrayList<Client> Clients = new ArrayList<>();
    ArrayList<City> Cities = new ArrayList<>();
    ArrayList<Job> Jobs = new ArrayList<>();
    ArrayList<Capacity> Capacities = new ArrayList<>();
    ArrayList<Category> Categories = new ArrayList<>();
    ArrayList<SubCategory> SubCategories = new ArrayList<>();

    ReadAndWriteToFile RW = new ReadAndWriteToFile();

    AutoCompleteTextView txtCity;
    AutoCompleteTextView txtJob;
    AutoCompleteTextView txtJobCapacity;
    TextView lblTitle;

    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Phone;
    EditText Address;

    EditText txtJobNumber;
    TextView txtCategory;
    TextView txtSubCategory;

    Switch sActive;

    Button butSubmit;
    int nmClient = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

        txtCity = findViewById(R.id.CityId);
        txtJob = findViewById(R.id.JobRequest);
        txtJobCapacity = findViewById(R.id.JobCapacityId);
        txtJobNumber = findViewById(R.id.JobRequestId);
        txtCategory = findViewById(R.id.JobFieldId);
        txtSubCategory = findViewById(R.id.SubFieldId);
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        Email = findViewById(R.id.Email);
        Phone = findViewById(R.id.Phone);
        Address = findViewById(R.id.Address);
        butSubmit = findViewById(R.id.butSubmit);
        lblTitle = findViewById(R.id.lblTitle);
        sActive =  findViewById(R.id.isActive);

        Cities = RW.ReadJsonFile(getApplicationContext(), "CityList");
        Jobs = RW.ReadJsonFile(getApplicationContext(), "JobList");
        Capacities = RW.ReadJsonFile(getApplicationContext(), "CapacityList");
        Categories = RW.ReadJsonFile(getApplicationContext(), "CategoryList");
        SubCategories = RW.ReadJsonFile(getApplicationContext(), "SubCategoryList");
        Clients = RW.ReadJsonFile(getApplicationContext(), "ClientList");

        CityLines CityAdapter = new CityLines(this, Cities);
        txtCity.setAdapter(CityAdapter);

        JobList JobAdapter = new JobList(this, Jobs);
        txtJob.setAdapter(JobAdapter);

        CapacityLines CapacityAdapter = new CapacityLines(this, Capacities);
        txtJobCapacity.setAdapter(CapacityAdapter);

        SelectedCity SC = new SelectedCity();
        txtCity.setOnItemSelectedListener(SC);
        txtCity.setOnItemClickListener(SC);
        txtCity.setOnFocusChangeListener(SC);


        SelectedCapacity SCap = new SelectedCapacity();
        txtJobCapacity.setOnItemSelectedListener(SCap);
        txtJobCapacity.setOnItemClickListener(SCap);
        txtJobCapacity.setOnFocusChangeListener(SCap);

        SelectedJob SJ = new SelectedJob();
        txtJob.setOnItemSelectedListener(SJ);
        txtJob.setOnItemClickListener(SJ);
        txtJob.setOnFocusChangeListener(SJ);

        SelectedJobNumber SJN = new SelectedJobNumber();
        txtJobNumber.setOnFocusChangeListener(SJN);


        try {
            nmClient = (int) getIntent().getSerializableExtra("nmClient");
        } catch (Exception e){
            nmClient = 0;
        }

        if (nmClient == 0){
            NewClient = new Client();
            FirstName.requestFocus();
        }else{
            for (Client CL : Clients) {
                if (CL.getClientId() == nmClient) {
                    NewClient = CL;
                    lblTitle.setText(CL.getFirstName() + " " + CL.getLastName());
                    break;
                }
            }

            FirstName.setText(NewClient.getFirstName());
            LastName.setText(NewClient.getLastName());
            Email.setText(NewClient.getEmail());
            Phone.setText(NewClient.getPhone());
            Address.setText(NewClient.getAddress());


            for (int i = 0; i < Cities.size() ; i++) {
                if (NewClient.getCityId() == Cities.get(i).getCityId()) {
                     txtCity.setText(Cities.get(i).getCity());
                    break;
                }
            }

            for (int i = 0; i < Jobs.size() ; i++) {
                if (NewClient.getJobRequestId() == Jobs.get(i).getJobId()) {
                    txtJob.setText(Jobs.get(i).getJob());
                    txtJobNumber.setText(Integer.toString(Jobs.get(i).getJobNumber()));
                    for (Category item : Categories) {
                        if (item.getCategoryId() == (Jobs.get(i).getCategoryId())) {
                            txtCategory.setText(item.getCategory());
                            break;
                        }
                    }
                    for (SubCategory item : SubCategories) {
                        if (item.getSubCategoryId() == (Jobs.get(i).getSubCategoryId())) {
                            txtSubCategory.setText(item.getSubCategory());
                            break;
                        }
                    }
                    break;
                }
            }

            for (int i = 0; i < Capacities.size() ; i++) {
                if (NewClient.getCityId() == Capacities.get(i).getJobCapacityId()) {
                    txtJobCapacity.setText(Capacities.get(i).getJobCapacity());
                    break;
                }
            }
        }

        sActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NewClient.setIsActive(isChecked);
            }
        });


        txtJobNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if((txtJob.getText().toString() != "") && !isChange){
                    isChange =true;
                    txtJob.setText("");
                    txtCategory.setText("");
                    txtSubCategory.setText("");
                    NewClient.setJobRequestId(0);
                    NewClient.setCategoryId(0);
                    NewClient.setSubCategoryId(0);
                }
                isChange=false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                NewClient.setCityId(0);
            }
        });

        txtJob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if((txtJobNumber.getText().toString() != "" && !isChange)) {
                    isChange = true;
                    txtJobNumber.setText("");
                    txtCategory.setText("");
                    txtSubCategory.setText("");
                    NewClient.setJobRequestId(0);
                    NewClient.setCategoryId(0);
                    NewClient.setSubCategoryId(0);
                }
                isChange=false;
            }
        });

        txtJobCapacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                NewClient.setJobTypeId(0);
            }
        });

        butSubmit.setOnClickListener(new View.OnClickListener() {
            private FileWriter file;

            @Override
            public void onClick(View v) {

                String strToast;

                if (FirstName.getText().toString().trim().equals("")) {
                    strToast = "Fill First Name";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    FirstName.requestFocus();
                    return;
                }

                if (LastName.getText().toString().trim().equals("")) {
                    strToast = "Fill Last Name";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    LastName.requestFocus();
                    return;
                }
                if (Email.getText().toString().trim().equals("")) {
                    strToast = "Fill Email";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    Email.requestFocus();
                    return;
                }
                if (Phone.getText().toString().trim().equals("")) {
                    strToast = "Fill Phone Number";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    Phone.requestFocus();
                    return;
                }
                if (NewClient.getCityId() == 0) {
                    strToast = "Fill City From List";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtCity.requestFocus();
                    return;
                }
                if (Address.getText().toString().trim().equals("")) {
                    strToast = "Fill Address";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    Address.requestFocus();
                    return;
                }

                if (NewClient.getJobRequestId() == 0) {
                    strToast = "Fill Job Request From List";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtJob.requestFocus();
                    return;
                }

                if (NewClient.getJobTypeId() == 0) {
                    strToast = "Fill Job Request From List";
                    Toast.makeText(getApplicationContext(), strToast, Toast.LENGTH_SHORT).show();
                    txtJobCapacity.requestFocus();
                    return;
                }


                NewClient.setFirstName(FirstName.getText().toString());
                NewClient.setLastName(LastName.getText().toString());
                NewClient.setEmail(Email.getText().toString());
                NewClient.setPhone(Phone.getText().toString());
                NewClient.setAddress(Address.getText().toString());

                NewClient.setJobRequestId(NewClient.getJobRequestId());
                NewClient.setCategoryId(NewClient.getCategoryId());
                NewClient.setSubCategoryId(NewClient.getSubCategoryId());

                if (nmClient == 0) {
                    NewClient.setClientId(Clients.size() + 1);
                    Clients.add(NewClient);
                }

                Gson GB = new GsonBuilder().create();
                String strJSON = GB.toJson(Clients);
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(strJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                RW.WriteToFile(getApplicationContext(),"ClientList", jsonArray);
                Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();

                Intent myIntent = new Intent(getApplicationContext(), ClientsActivity.class);
                myIntent.putExtra("activity","ClientDetailsActivity");
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
            NewClient.setCityId(SelectedCity.getCityId());
            Address.requestFocus();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectedCity = (City) txtCity.getAdapter().getItem(position);
            NewClient.setCityId(SelectedCity.getCityId());
            Address.requestFocus();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewClient.setCityId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            //NewClient.setCityId(0);
        }
    }

    //SELECT JOB
    class SelectedJob implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
         private Job SelectedJob;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SelectedJob = (Job) txtJob.getAdapter().getItem(position);
            setClientJob();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectedJob = (Job) txtJob.getAdapter().getItem(position);
            setClientJob();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //deleteClientJob();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            //deleteClientJob();
        }

        private void setClientJob() {
            isChange =true;
            txtJobNumber.setText(Integer.toString(SelectedJob.getJobNumber()));

            System.out.println(SelectedJob.getJobId());
            System.out.println(SelectedJob.getCategoryId());
            System.out.println(SelectedJob.getSubCategoryId());

            NewClient.setJobRequestId(SelectedJob.getJobId());
            NewClient.setCategoryId(SelectedJob.getCategoryId());
            NewClient.setSubCategoryId(SelectedJob.getSubCategoryId());

            for (Category item : Categories) {
                if (item.getCategoryId() == (this.SelectedJob.getCategoryId())) {
                    txtCategory.setText(item.getCategory());
                    break;
                }
            }
            for (SubCategory item : SubCategories) {
                if (item.getSubCategoryId() == (this.SelectedJob.getSubCategoryId())) {
                    txtSubCategory.setText(item.getSubCategory());
                    break;
                }
            }
            txtJobCapacity.requestFocus();
        }
    }

    //SELECT CAPACITY
    class SelectedCapacity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        private Capacity SelectedCapacity;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SelectedCapacity = (Capacity) txtJobCapacity.getAdapter().getItem(position);
            NewClient.setJobTypeId(SelectedCapacity.getJobCapacityId());
            butSubmit.setFocusable(true);
            butSubmit.setFocusableInTouchMode(true);
            butSubmit.onEditorAction(IME_ACTION_DONE);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SelectedCapacity = (Capacity) txtJobCapacity.getAdapter().getItem(position);
            NewClient.setJobTypeId(SelectedCapacity.getJobCapacityId());
            butSubmit.setFocusable(true);
            butSubmit.setFocusableInTouchMode(true);
            butSubmit.onEditorAction(IME_ACTION_DONE);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewClient.setJobTypeId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            //NewClient.setJobTypeId(0);
        }
    }

    //SELECT JOB NUMBER
    class SelectedJobNumber implements EditText.OnFocusChangeListener{
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if ((txtJobNumber.getText().toString() != "") && !hasFocus){
                for (int i = 0; i < Jobs.size(); i++) {

                    if (Integer.parseInt(txtJobNumber.getText().toString()) == Jobs.get(i).getJobNumber()) {
                        isChange =true;
                        txtJob.setText(Jobs.get(i).getJob());
                        NewClient.setJobRequestId(Jobs.get(i).getJobId());

                        for (Category item : Categories) {
                            if (item.getCategoryId() == (Jobs.get(i).getCategoryId())) {
                                txtCategory.setText(item.getCategory());
                                NewClient.setCategoryId(Jobs.get(i).getCategoryId());
                                break;
                            }
                        }
                        for (SubCategory item : SubCategories) {
                            if (item.getSubCategoryId() == (Jobs.get(i).getSubCategoryId())) {
                                txtSubCategory.setText(item.getSubCategory());
                                NewClient.setSubCategoryId(Jobs.get(i).getSubCategoryId());
                                break;
                            }
                        }
                        break;
                    }
                }

            }
        }
    }

}
