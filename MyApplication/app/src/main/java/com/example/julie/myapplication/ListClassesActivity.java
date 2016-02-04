package com.example.julie.myapplication;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import model.HelperDB;
import model.Lesson;

public class ListClassesActivity extends AppCompatActivity {
    TableLayout tableClasses;
    String day;
    TextView today;
    int group;
    HelperDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_classes);

        day = getIntent().getStringExtra("day");
        group = getIntent().getIntExtra("group", 0);

        tableClasses = (TableLayout) this.findViewById(R.id.tableClasses);
        today = (TextView) findViewById(R.id.tvDay);
        today.setText(day);
        writeDaySchedule(day);
    }


    void writeDaySchedule(String day){
        dbHelper = new HelperDB(this,"schedule",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.query("schedule", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int dayColIndex = c.getColumnIndex("day");
            int nameColIndex = c.getColumnIndex("name");
            int roomColIndex = c.getColumnIndex("room");
            int timestartColIndex = c.getColumnIndex("stime");
            int timeendColIndex = c.getColumnIndex("etime");

            do {
                if(c.getString(dayColIndex).equals(day))
                    addLesson(new Lesson(c.getString(dayColIndex),
                            c.getString(nameColIndex),
                            c.getString(roomColIndex),
                            c.getString(timestartColIndex),
                            c.getString(timeendColIndex)));

            } while (c.moveToNext());

            c.close();
        }
    }


    void addLesson(Lesson les) {
        TableRow row = new TableRow(new ContextThemeWrapper(ListClassesActivity.this, R.style.Table));
        TextView timeText = new TextView(new ContextThemeWrapper(ListClassesActivity.this, R.style.CellSchedule));
        TextView nameText = new TextView(new ContextThemeWrapper(ListClassesActivity.this, R.style.CellSchedule));
        TextView roomText = new TextView(new ContextThemeWrapper(ListClassesActivity.this, R.style.CellSchedule));
        timeText.setText(les.getTimeStart() + " - " + les.getTimeEnd());
        nameText.setText(les.getName());
        roomText.setText(les.getRoom());
        row.addView(timeText);
        row.addView(nameText);
        row.addView(roomText);
        tableClasses.addView(row);
    }
}
