package com.example.testapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import com.example.testapp.ArrayAdapters.CapacityLines;
import com.example.testapp.ArrayAdapters.StudiesLines;
import com.example.testapp.ArrayAdapters.StudiesTypeLines;
import com.example.testapp.ArrayAdapters.SubCategoryLines;
import com.example.testapp.ArrayAdapters.SubStudiesLines;
import com.example.testapp.ClassObjects.Capacity;
import com.example.testapp.ClassObjects.Studies;
import com.example.testapp.ClassObjects.Studies;
import com.example.testapp.ClassObjects.StudiesType;
import com.example.testapp.ClassObjects.SubStudies;
import com.example.testapp.ClassObjects.SubStudies;

import java.util.ArrayList;

public class EducationPopupActivity extends Activity {

    ArrayList<Studies> studies = new ArrayList<>();
    ArrayList<StudiesType> studiesType = new ArrayList<>();
    ArrayList<SubStudies> subStudies = new ArrayList<>();

    AutoCompleteTextView txtStudies, txtStudiesType, txtSubStudies;
    SubStudiesLines subStudiesAdapter;
    ReadAndWriteToFile RW = new ReadAndWriteToFile();


    public void setFocus(View view) {
        view.requestFocus();
    }

    public void dropDown(AutoCompleteTextView view) {
        view.showDropDown();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_popup);

        txtStudies = findViewById(R.id.txtStudies);
        txtStudiesType = findViewById(R.id.txtStudiesType);
        txtSubStudies = findViewById(R.id.txtSubStudies);

        studies = RW.ReadJsonFile(getApplicationContext(), "StudiesList");
        subStudies = RW.ReadJsonFile(getApplicationContext(), "SubStudiesList");
        studiesType = RW.ReadJsonFile(getApplicationContext(), "StudiesTypeList");

        StudiesLines StudiesAdapter = new StudiesLines(this, studies);
        txtStudies.setAdapter(StudiesAdapter);

        SubStudiesLines SubStudiesAdapter = new SubStudiesLines(this, subStudies);
        txtStudiesType.setAdapter(SubStudiesAdapter);

        StudiesTypeLines StudiesTypeAdapter = new StudiesTypeLines(this, studiesType);
        txtSubStudies.setAdapter(StudiesTypeAdapter);

        EducationPopupActivity.SelectedStudyType SST = new EducationPopupActivity.SelectedStudyType();
        txtStudiesType.setOnItemSelectedListener(SST);
        txtStudiesType.setOnItemClickListener(SST);
        txtStudiesType.setOnFocusChangeListener(SST);

        EducationPopupActivity.SelectedStudies SS = new EducationPopupActivity.SelectedStudies();
        txtStudies.setOnItemSelectedListener(SS);
        txtStudies.setOnItemClickListener(SS);
        txtStudies.setOnFocusChangeListener(SS);

        EducationPopupActivity.SelectedSubStudies SSS = new EducationPopupActivity.SelectedSubStudies();
        txtSubStudies.setOnItemSelectedListener(SSS);
        txtSubStudies.setOnItemClickListener(SSS);
        txtSubStudies.setOnFocusChangeListener(SSS);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int popupWidth = dm.widthPixels;
        int popupHeight = dm.heightPixels;

        getWindow().setLayout((int) (popupWidth * 0.8), (int) (popupHeight * 0.4));

        WindowManager.LayoutParams LParams = getWindow().getAttributes();
        LParams.x = 0;
        LParams.y = -150;

        getWindow().setAttributes(LParams);
    }


    //SELECT STUDIES TYPE
    class SelectedStudyType implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        private StudiesType studyType;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            studyType = (StudiesType) txtStudiesType.getAdapter().getItem(position);
            //NewJob.setJobCapacityId(studyType.getStudyTypeID());
            setFocus(txtStudies);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            studyType = (StudiesType) txtStudiesType.getAdapter().getItem(position);
            //NewJob.setJobCapacityId(studyType.getStudyTypeID());
            txtStudies.requestFocus();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewJob.setJobTypeId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (txtStudiesType.hasFocus()) {
                dropDown(txtStudiesType);
            }
        }
    }

    //SELECT STUDIES
    class SelectedStudies implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        private Studies selectedStudies;

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedStudies = (Studies) txtStudies.getAdapter().getItem(position);
            subStudiesAdapter.setStudiesId(selectedStudies.getStudyId());
            txtSubStudies.setAdapter(subStudiesAdapter);
            txtSubStudies.refreshAutoCompleteResults();
            setFocus(txtSubStudies);
        }

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedStudies = (Studies) txtStudies.getAdapter().getItem(position);
            subStudiesAdapter.setStudiesId(selectedStudies.getStudyId());
            txtSubStudies.setAdapter(subStudiesAdapter);
            txtSubStudies.refreshAutoCompleteResults();
            setFocus(txtSubStudies);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewJob.setJobTypeId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (txtStudies.hasFocus()) {
                dropDown(txtStudies);
            }
        }
    }

    //SELECT SUB STUDIES
    class SelectedSubStudies implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener, AdapterView.OnFocusChangeListener {
        private SubStudies selectedSubStudies;

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedSubStudies = (SubStudies) txtSubStudies.getAdapter().getItem(position);
            setFocus(txtSubStudies);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedSubStudies = (SubStudies) txtSubStudies.getAdapter().getItem(position);
            setFocus(txtSubStudies);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            //NewJob.setJobTypeId(0);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (txtSubStudies.hasFocus()) {
//                dropDown(txtSubStudies);
            }
        }

    }

}
