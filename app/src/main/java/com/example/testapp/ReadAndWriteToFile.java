package com.example.testapp;

import android.app.Application;
import android.content.Context;

import com.example.testapp.ClassObjects.Capacity;
import com.example.testapp.ClassObjects.Category;
import com.example.testapp.ClassObjects.City;
import com.example.testapp.ClassObjects.Client;
import com.example.testapp.ClassObjects.Job;
import com.example.testapp.ClassObjects.SubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ReadAndWriteToFile {


    public void WriteToFile(Context context, String FileName,  JSONArray arrJSON){

        JSONObject JSONObj= new JSONObject();
        try {
            JSONObj.put(FileName,arrJSON);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FileName,MODE_PRIVATE);

            System.out.println(context.getFilesDir());
            fos.write(JSONObj.toString().getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList ReadJsonFile(Context context, String FileName){

        String strJSON = null;
        ArrayList AL = new ArrayList<>();
        try {
            FileInputStream fis = null;
            fis = context.openFileInput(FileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String txtLine;

            while ((txtLine = br.readLine()) != null){
                sb.append(txtLine).append("\n");
            }
            strJSON = sb.toString();

            if (fis!=null) {
                fis.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try{
            if (strJSON != null) {
                JSONObject JSONCities = new JSONObject(strJSON);
                JSONArray JC = JSONCities.getJSONArray(FileName);

                for (int i = 0; i < JC.length(); ++i) {
                    JSONObject JObject = JC.getJSONObject(i);
                    switch (FileName) {
                        case "CityList":
                            AL.add(new City(JObject.getInt("CityId"), JObject.getString("City"), JObject.getInt("AreaId")));
                            break;
                        case "JobList":
                            AL.add(new Job(JObject.getInt("JobId"), JObject.getInt("JobNumber"), JObject.getString("Job"), JObject.getInt("CityId"), JObject.getInt("AreaId"), JObject.getInt("JobCapacityId"), JObject.getInt("CategoryId"), JObject.getInt("SubCategoryId"), JObject.getBoolean("IsActive")));
                            break;
                        case "CapacityList":
                            AL.add(new Capacity(JObject.getInt("JobCapacityId"), JObject.getString("JobCapacity")));
                            break;
                        case "CategoryList":
                            AL.add(new Category(JObject.getInt("CategoryId"), JObject.getString("Category")));
                            break;
                        case "SubCategoryList":
                            AL.add(new SubCategory(JObject.getInt("SubCategoryId"), JObject.getString("SubCategory"), JObject.getInt("CategoryId")));
                            break;
                        case "ClientList":
                            AL.add(new Client(JObject.getInt("ClientId"), JObject.getString("FirstName"), JObject.getString("LastName"), JObject.getString("Email"), JObject.getInt("CityId"), JObject.getString("Address"),
                                    JObject.getInt("AreaId"), JObject.getInt("JobRequestId"), JObject.getInt("JobTypeId"), JObject.getInt("CategoryId"), JObject.getInt("SubCategoryId"),
                                    JObject.getInt("EducationId"), JObject.getString("Phone"), JObject.getBoolean("IsActive") , JObject.getBoolean("IsFlag") ));
                            break;
                    }
                }
            }
        } catch (JSONException ex1) {
            ex1.printStackTrace();
        }
        return AL;
    }




}
