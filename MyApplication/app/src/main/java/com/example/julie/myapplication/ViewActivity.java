package com.example.julie.myapplication;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import model.Lesson;

public class ViewActivity extends AppCompatActivity {

    public ArrayList<Button> week = new ArrayList<Button>();
    public String message;
    int group = 0;
    int day_id = 0;
    String btnText = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        week.add((Button) findViewById(R.id.btnMonday));
        week.add((Button) findViewById(R.id.btnTuesday));
        week.add((Button) findViewById(R.id.btnWednesday));
        week.add((Button) findViewById(R.id.btnThursday));
        week.add((Button) findViewById(R.id.btnFriday));
        week.add((Button) findViewById(R.id.btnSaturday));


        day_id = 0;
        group = getIntent().getIntExtra("group", 0);
        System.out.println("VIEW GROUP: " + group);

        /*final Intent intent = new Intent(this, ListClassesActivity.class);*/
        for (final Button btn : week) {
            day_id = 2;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(ViewActivity.this, ListClassesActivity.class);
                    intent.putExtra("day", btn.getText().toString());
                    intent.putExtra("day_id", day_id);
                    intent.putExtra("group", group);
                    startActivity(intent);

                    /*new RequestTask().execute("http://192.168.0.136:8080/users/classes");*/
                }
            });
        }
    }



    @Override
    public void onBackPressed() {
        ViewActivity.this.finish();
        //startActivity(new Intent(this, MainActivity.class));
    }
}

