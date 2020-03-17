package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.testapp.ArrayAdapters.ClientLines;
import com.example.testapp.ArrayAdapters.JobLines;
import com.example.testapp.ClassObjects.Category;
import com.example.testapp.ClassObjects.Client;
import com.example.testapp.ClassObjects.Job;
import com.example.testapp.ClassObjects.SubCategory;

import java.util.ArrayList;

public class JobsActivity extends AppCompatActivity {

    ArrayList Jobs = new ArrayList<>();
    ArrayList Categories = new ArrayList<>();
    ArrayList SubCategories = new ArrayList<>();

    EditText txtSearch;
    ListView lvJobs;
    Switch s_isActive;
    Button butAdd, butMain;
    JobLines adapter;
    String LastActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

        txtSearch = findViewById(R.id.txtSearch);
        lvJobs = findViewById(R.id.lvJobs);
        s_isActive = findViewById(R.id.s_isActive);
        butAdd = findViewById(R.id.butAdd );
        butMain = findViewById(R.id.butMain );

        Intent intent = getIntent();
        LastActivity = intent.getStringExtra("activity");

        ReadAndWriteToFile RW = new ReadAndWriteToFile();
        Jobs = RW.ReadJsonFile(getApplicationContext(),"JobList");
        Categories = RW.ReadJsonFile(getApplicationContext(),"CategoryList");
        SubCategories = RW.ReadJsonFile(getApplicationContext(),"SubCategoryList");


        adapter = new JobLines(this, Jobs,Categories,SubCategories);
        lvJobs.setAdapter(adapter);

        butAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), JobDetailsActivity.class);
                startActivity(myIntent);
            }
        });

        butMain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        s_isActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                YoYo.with(Techniques.Shake).delay(0).duration(1000).repeat(0).playOn(lvJobs);
                adapter.setActive(isChecked);
                adapter.getFilter().filter(txtSearch.getText());
                adapter.notifyDataSetChanged();
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvJobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Job ObjJobs;
                int nmJobs;

                ObjJobs = (Job) lvJobs.getAdapter().getItem(position);
                nmJobs = ObjJobs.getJobId();

                Intent myIntent = new Intent(getApplicationContext(), JobDetailsActivity.class);

                myIntent.putExtra("nmJob",nmJobs);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!LastActivity.equals("JobDetailsActivity")){
            super.onBackPressed();
        }
    }
}
