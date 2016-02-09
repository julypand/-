package com.example.julie.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import model.HelperDB;
import model.Lesson;

public class ListClassesActivity extends AppCompatActivity {
    TableLayout tableClasses;
    String day;
    TextView today;
    int group;
    HelperDB dbHelper;
    SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_classes);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        day = getIntent().getStringExtra("day");
        group =  loginPreferences.getInt("group",1);

        tableClasses = (TableLayout) this.findViewById(R.id.tableClasses);
        today = (TextView) findViewById(R.id.tvDay);
        today.setText(day);
        writeDaySchedule(day);
    }


    void writeDaySchedule(String day){
        dbHelper = new HelperDB(this,"schedule",null,1);
        ArrayList<Lesson> lessonsOfDay = dbHelper.readScheduleOfDay(day);
        addLessons(lessonsOfDay);
    }


    void addLessons(ArrayList<Lesson> lessons) {
        for(Lesson les : lessons) {
            TableRow row = new TableRow(new ContextThemeWrapper(ListClassesActivity.this, R.style.Table));
            TextView timeText = new TextView(new ContextThemeWrapper(ListClassesActivity.this, R.style.CellSchedule));
            TextView nameText = new TextView(new ContextThemeWrapper(ListClassesActivity.this, R.style.CellSchedule));
            TextView roomText = new TextView(new ContextThemeWrapper(ListClassesActivity.this, R.style.CellSchedule));
            TextView typeText = new TextView(new ContextThemeWrapper(ListClassesActivity.this, R.style.CellSchedule));

            timeText.setText(les.getTimeStart() + " - " + les.getTimeEnd());
            nameText.setText(les.getName());
            roomText.setText(les.getRoom());
            typeText.setText(les.getType());
            row.addView(timeText);
            row.addView(nameText);
            row.addView(roomText);
            row.addView(typeText);
            tableClasses.addView(row);
        }
    }
}
