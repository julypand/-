package com.example.julie.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import model.HelperDB;


public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSignUp;
    SharedPreferences loginPreferences;
    Boolean isSaveLogin;
    HelperDB dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        isSaveLogin = loginPreferences.getBoolean("saveLogin", false);
        if(isSaveLogin){
            MainActivity.this.finish();
            startActivity(new Intent(this, ViewActivity.class));
        }
        else {
            dbHelper = new HelperDB(this,"schedule",null,1);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("DELETE FROM schedule");
            dbHelper.close();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        final Intent intentLogin = new Intent(this, LoginActivity.class);
        final Intent intentSignUp = new Intent(this, SignupActivity.class);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity.this.finish();
                startActivity(intentLogin);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MainActivity.this.finish();
                startActivity(intentSignUp);
            }
        });
           }

}
