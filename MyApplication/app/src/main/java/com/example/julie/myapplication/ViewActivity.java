package com.example.julie.myapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    public ArrayList<Button> week = new ArrayList<Button>();
    public String message;

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

        final Intent intent = new Intent(this, ListClassesActivity.class);

        for (final Button btn : week) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra("day",btn.getText().toString());
                    startActivity(intent);
                }
            });



        }


    }
}

