package com.example.julie.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import model.HelperDB;
import model.RequestTaskClasses;


public class ViewActivity extends AppCompatActivity {

    Button btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;
    String ip;
    int group = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);

        group = getIntent().getIntExtra("group", 0);

        btnMonday = (Button) findViewById(R.id.btnMonday);
        btnTuesday = (Button) findViewById(R.id.btnTuesday);
        btnWednesday = (Button) findViewById(R.id.btnWednesday);
        btnThursday = (Button) findViewById(R.id.btnThursday);
        btnFriday = (Button) findViewById(R.id.btnFriday);
        btnSaturday = (Button) findViewById(R.id.btnSaturday);


        btnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  goDay(getResources().getString(R.string.monday));
            }
        });
        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(getResources().getString(R.string.tuesday));
            }
        });
        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(getResources().getString(R.string.wednesday));
            }
        });
        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(getResources().getString(R.string.thursday));
            }
        });
        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(getResources().getString(R.string.friday));
            }
        });
        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay(getResources().getString(R.string.saturday));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    public void goDay(String day){
        final Intent intent = new Intent(ViewActivity.this, ListClassesActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        int id = item.getItemId();
        if(id == R.id.logoff){
            loginPrefEditor = loginPreferences.edit();
            loginPrefEditor.putBoolean("saveLogin", false);
            loginPrefEditor.commit();
            ViewActivity.this.finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(id == R.id.refresh){
            HelperDB dbHelper = new HelperDB(getApplicationContext(),"schedule",null,1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("DELETE FROM schedule");
            ProgressDialog pDialog = new ProgressDialog(ViewActivity.this, R.style.AppTheme);
            group = loginPreferences.getInt("group",1);
            new RequestTaskClasses(ViewActivity.this,getBaseContext(),pDialog,group).execute(getResources().getString(R.string.ip) + "/users/classes");
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        ViewActivity.this.finish();
    }
}

