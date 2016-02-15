package com.example.julie.myapplication;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import java.util.ArrayList;

import model.HelperDB;
import model.RecyclerViewAdapter;
import model.RequestTaskClasses;
import model.User;

public class ScheduleListActivity extends AppCompatActivity {
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;
    String ip;
    HelperDB dbHelper;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(getDataSet(),ScheduleListActivity.this);
        mRecyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        int id = item.getItemId();
        if(id == R.id.logoff){
            loginPrefEditor = loginPreferences.edit();
            loginPrefEditor.putBoolean("saveLogin", false);
            loginPrefEditor.commit();
            ScheduleListActivity.this.finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(id == R.id.refresh){
            HelperDB dbHelper = new HelperDB(getApplicationContext(),"schedule",null,1);
            dbHelper.clear();
            String email = loginPreferences.getString("email", "");
            ip = getResources().getString(R.string.ip);
            new RequestTaskClasses(ScheduleListActivity.this,getBaseContext(),new User(email)).execute(ip + "/users/classes");
        }
        return super.onOptionsItemSelected(item);
    }
    private ArrayList<String> getDataSet() {
        dbHelper = new HelperDB(getBaseContext(),"schedule",null,1);
        return dbHelper.getNameSchedules();

    }




}
