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

import com.example.testapp.ArrayAdapters.ClientLines;
import com.example.testapp.ClassObjects.City;
import com.example.testapp.ClassObjects.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ClientsActivity extends AppCompatActivity {

    ListView lvClients;
    ArrayList<Client> Clients = new ArrayList<>();

    Button butAdd, butMain;
    Switch s_isActive;
    Switch s_isFlag;
    ClientLines adapter;
    EditText txtSearch;
    String LastActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        txtSearch = findViewById(R.id.txtSearch);
        lvClients = findViewById(R.id.lvClients);
        s_isActive = findViewById(R.id.s_isActive);
        s_isFlag = findViewById(R.id.s_isFlag );
        butAdd = findViewById(R.id.butAdd );
        butMain = findViewById(R.id.butMain );

        Intent intent = getIntent();
        LastActivity = intent.getStringExtra("activity");


        ReadAndWriteToFile RW = new ReadAndWriteToFile();
        Clients = RW.ReadJsonFile(getApplicationContext(), "ClientList");

        adapter = new ClientLines(this,Clients);
        lvClients.setAdapter(adapter);

        butAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ClientDetailsActivity.class);
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
                adapter.setActive(isChecked);
                adapter.getFilter().filter(txtSearch.getText());
                adapter.notifyDataSetChanged();
            }
        });

        s_isFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                adapter.setFlag(isChecked);
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

        lvClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client ObjClient;
                int nmClient;

                ObjClient = (Client) lvClients.getAdapter().getItem(position);
                nmClient = ObjClient.getClientId();

                Intent myIntent = new Intent(getApplicationContext(), ClientDetailsActivity.class);

                myIntent.putExtra("nmClient",nmClient);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!LastActivity.equals("ClientDetailsActivity")){
            super.onBackPressed();
        }
    }

    private void setListAdapter(){
        this.adapter = new ClientLines(this,Clients);
        lvClients.setAdapter(adapter);
    }

//    private ArrayList<Client> readJsonClients() {
//        ArrayList<Client> ClientArr = new ArrayList<>();
//        try {
//            InputStream is = getAssets().open("ClientList");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            String strJSON = new String(buffer, "UTF-8");
//
//            JSONObject JSONCities = new JSONObject(strJSON);
//            JSONArray JC = JSONCities.getJSONArray("ClientList");
//
//            for (int i = 0; i < JC.length(); ++i) {
//                JSONObject JClient = JC.getJSONObject(i);
//                ClientArr.add(new Client(JClient.getInt("ClientId"), JClient.getString("FirstName"), JClient.getString("LastName"),JClient.getInt("IsFlag")==1));
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//        return ClientArr;
//    }

}
