package com.example.julie.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import model.HelperDB;
import model.Lesson;

public class ListClassesActivity extends AppCompatActivity {
    TableLayout tableClasses;
    Button btnLeft, btnRight;
    int day_id;
    TextView today;
    int group;
    HelperDB dbHelper;
    SharedPreferences loginPreferences;
    String[] week = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_classes);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        day_id = getIntent().getIntExtra("day_id", 0);
        group =  loginPreferences.getInt("group", 1);

        tableClasses = (TableLayout) this.findViewById(R.id.tableClasses);
        today = ( TextView) findViewById(R.id.tvDay);
        btnLeft = (Button) this.findViewById(R.id.btnleft);
        btnRight = (Button) this.findViewById(R.id.btnright);

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day_right_id = (day_id + 1) % 6;
                goDay(day_right_id);
            }
        });
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day_right_id = (day_id + 5) % 6;
                goDay(day_right_id);
            }
        });
        today.setText(week[day_id]);
        writeDaySchedule(week[day_id]);
    }
    public void goDay(int day_id){
        final Intent intent = new Intent(ListClassesActivity.this, ListClassesActivity.class);
        intent.putExtra("day_id", day_id);
        startActivity(intent);
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
