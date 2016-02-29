package com.example.julie.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import model.HelperDB;
import model.RequestTaskClasses;
import model.User;


public class ViewActivity extends AppCompatActivity {

    Button btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday;
    ArrayList<Button> weekBtn;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;
    HelperDB dbHelper;
    ArrayList<String> week;
    String ip;
    String name_schedule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        name_schedule = loginPreferences.getString("name_schedule","");

        dbHelper = new HelperDB(getApplicationContext(),"schedule",null,1);
        week = dbHelper.getWeek();

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        topToolBar.setTitle(name_schedule);
        setSupportActionBar(topToolBar);


        btnMonday = (Button) findViewById(R.id.btnMonday);
        btnTuesday = (Button) findViewById(R.id.btnTuesday);
        btnWednesday = (Button) findViewById(R.id.btnWednesday);
        btnThursday = (Button) findViewById(R.id.btnThursday);
        btnFriday = (Button) findViewById(R.id.btnFriday);
        btnSaturday = (Button) findViewById(R.id.btnSaturday);

        btnMonday.setText(week.get(0));
        btnTuesday.setText(week.get(1));
        btnWednesday.setText(week.get(2));
        btnThursday.setText(week.get(3));
        btnFriday.setText(week.get(4));
        btnSaturday.setText(week.get(5));




        btnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  goDay(0);
            }
        });
        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(1);
            }
        });
        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(2);
            }
        });
        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(3);
            }
        });
        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(4);
            }
        });
        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(5);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    public void goDay(int day_id){
        final Intent intent = new Intent(ViewActivity.this, ClassesListActivity.class);
        intent.putExtra("day_id", day_id);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        int id = item.getItemId();
        if(id == R.id.logoff){
            HelperDB dbHelper = new HelperDB(getApplicationContext(),"schedule",null,1);
            dbHelper.clear();
            loginPrefEditor = loginPreferences.edit();
            loginPrefEditor.putBoolean("saveLogin", false);
            loginPrefEditor.commit();
            ViewActivity.this.finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(id == R.id.refresh){
            HelperDB dbHelper = new HelperDB(getApplicationContext(),"schedule",null,1);
            dbHelper.clear();
            String email = loginPreferences.getString("email", "");
            ip = getResources().getString(R.string.ip);
            new RequestTaskClasses(ViewActivity.this,getBaseContext(),new User(email)).execute(ip + "/users/classes");
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        ViewActivity.this.finish();
        startActivity(new Intent(this,ScheduleListActivity.class));
    }
}

