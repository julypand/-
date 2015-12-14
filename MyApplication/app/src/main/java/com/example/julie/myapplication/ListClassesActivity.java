package com.example.julie.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ListClassesActivity extends AppCompatActivity {
    TableLayout tableClasses;
    String day;
    TextView today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_classes);

        day = getIntent().getStringExtra("day");
        tableClasses = (TableLayout) this.findViewById( R.id.tableClasses);
        today = (TextView)findViewById(R.id.tvDay);
        today.setText(day);
    }

    public void onStart() {
        super.onStart();
    }


}
