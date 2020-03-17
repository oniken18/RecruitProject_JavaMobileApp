package com.example.testapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    int countX = 0;

    Button butSearch , butJobs, butClients;
    Toolbar TopToolbar;
    DrawerLayout DL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        butSearch = findViewById(R.id.butSearch);
        butJobs = findViewById(R.id.butJobs);
        butClients = findViewById(R.id.butClients);
        DL = findViewById(R.id.drawer_layout);

        TopToolbar = findViewById(R.id.TopToolbar);
        setSupportActionBar(TopToolbar);
        TopToolbar.setTitle("String");



        YoYo.with(Techniques.FlipInX).delay(50).duration(700).repeat(0).playOn(butClients);
        YoYo.with(Techniques.FlipInX).delay(250).duration(700).repeat(0).playOn(butJobs);
        YoYo.with(Techniques.FlipInX).delay(450).duration(700).repeat(0).playOn(butSearch);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, DL,TopToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        DL.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if(DL.isDrawerOpen(GravityCompat.START)){
            DL.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    public void butClients_OnClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   ClientsActivity.class);
        startActivity(myIntent);
    }

    public void butJobs_OnClick(View v){
        Intent myIntent = new Intent(getBaseContext(),   JobsActivity.class);
        startActivity(myIntent);
    }

}
