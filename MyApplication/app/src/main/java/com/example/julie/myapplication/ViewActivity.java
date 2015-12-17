package com.example.julie.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;


public class ViewActivity extends AppCompatActivity {

    public ArrayList<Button> week = new ArrayList<>();
    int group = 0;


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
        group = getIntent().getIntExtra("group", 0);

        for (final Button btn : week) {

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(ViewActivity.this, ListClassesActivity.class);
                    intent.putExtra("day", btn.getText().toString());
                    intent.putExtra("group", group);
                    startActivity(intent);
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        ViewActivity.this.finish();
    }
}

