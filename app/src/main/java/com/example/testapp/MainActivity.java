package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Button butSearch, butJobs, butClients;
    Toolbar TopToolbar;
    DrawerLayout DL;
    NavigationView nav_view;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        butSearch = findViewById(R.id.butSearch);
        butJobs = findViewById(R.id.butJobs);
        butClients = findViewById(R.id.butClients);
        DL = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(this);

        TopToolbar = findViewById(R.id.TopToolbar);
        TopToolbar.setTitle("");
        setSupportActionBar(TopToolbar);

        YoYo.with(Techniques.FlipInX).delay(50).duration(700).repeat(0).playOn(butClients);
        YoYo.with(Techniques.FlipInX).delay(250).duration(700).repeat(0).playOn(butJobs);
        YoYo.with(Techniques.FlipInX).delay(450).duration(700).repeat(0).playOn(butSearch);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, DL, TopToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        DL.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        i = new Intent(getBaseContext(), TableActivity.class);
        switch (item.getItemId()) {
            case R.id.tblCities:
                i.putExtra("tblName", "CityList");
                break;
            case R.id.tblCapacities:
                i.putExtra("tblName", "CapacityList");
                break;
            case R.id.tblCategories:
                i.putExtra("tblName", "CategoryList");
                break;
            case R.id.tblSubCategories:
                i.putExtra("tblName", "SubCategoryList");
                break;
            case R.id.tblStudiesTypes:
                i.putExtra("tblName", "StudiesTypeList");
                break;
            case R.id.tblStudies:
                i.putExtra("tblName", "StudiesList");
                break;
            case R.id.tblSubStudies:
                i.putExtra("tblName", "SubStudiesList");
                break;
        }

        DL.closeDrawers();
        startActivity(i);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (DL.isDrawerOpen(GravityCompat.START)) {
            DL.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void butClients_OnClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), ClientsActivity.class);
        myIntent.putExtra("activity", "MainActivity");
        startActivity(myIntent);
    }

    public void butJobs_OnClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), JobsActivity.class);
        myIntent.putExtra("activity", "MainActivity");
        startActivity(myIntent);
    }

    public void DelDB(View v) {
//        ReadAndWriteToFile RW = new ReadAndWriteToFile();
//        String[] tables = {"CapacityList", "CategoryList", "CityList", "ClientList", "JobList", "StudiesList", "StudiesTypeList", "SubCategoryList", "SubStudiesList"};
//        String[] tables = {"StudiesList","SubStudiesList"};
//        for (String item : tables) {
//            RW.WriteToFile(this, item, new JSONArray());
//        }
    }
}
