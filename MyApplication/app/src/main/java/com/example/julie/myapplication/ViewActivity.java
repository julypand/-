package com.example.julie.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ViewActivity extends AppCompatActivity {

    Button btnMonday, btnTuesday, btnWednesday, btnThursday, btnFriday, btnSaturday;
    int group = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

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
                  goDay("Monday");
            }
        });
        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay("Tuesday");
            }
        });
        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay("Wednesday");
            }
        });
        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay("Thursday");
            }
        });
        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay("Friday");
            }
        });
        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDay("Saturday");
            }
        });

    }
    public void goDay(String day){
        final Intent intent = new Intent(ViewActivity.this, ListClassesActivity.class);
        intent.putExtra("day", day);
        intent.putExtra("group", group);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        ViewActivity.this.finish();
    }
}

