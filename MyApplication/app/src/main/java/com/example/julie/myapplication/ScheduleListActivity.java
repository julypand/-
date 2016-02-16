package com.example.julie.myapplication;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


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
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(getDataSet(),ScheduleListActivity.this);
        mRecyclerView.setAdapter(mAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    public void showAddDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(getResources().getString(R.string.enter_name));

        View linearlayout = getLayoutInflater().inflate(R.layout.dialog_newschedule, null);
        dialog.setView(linearlayout);

        final TextView name_schedule = (TextView)linearlayout.findViewById(R.id.etNameSchedule);

        dialog.setPositiveButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                })

                .setNegativeButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String name = name_schedule.getText().toString();
                                addSchedule(name);
                                dialog.dismiss();
                            }
                        });

        dialog.create();
        dialog.show();
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
        ArrayList<String> ff = dbHelper.getNameSchedules();
        return dbHelper.getNameSchedules();

    }
    private void addSchedule(String name){
        dbHelper = new HelperDB(getBaseContext(),"schedule",null,1);

        dbHelper.addSchedule(name);
        //mRecyclerView.
        mAdapter.addItem(name);
    }




}

