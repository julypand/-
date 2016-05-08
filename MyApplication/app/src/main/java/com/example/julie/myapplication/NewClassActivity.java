package com.example.julie.myapplication;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import model.HelperDB;
import model.Lesson;

public class NewClassActivity extends AppCompatActivity {
    int day_id;
    String name_schedule;
    Button saveBtn, cancelBtn;
    SharedPreferences loginPreferences;
    EditText nameText, roomText,sTimeText,eTimeText,typeText;
    boolean isStart = true;
    String name, room,stime,etime, type;
    Calendar dateAndTime = Calendar.getInstance();
    HelperDB helperDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_class);

        helperDB = new HelperDB(getApplicationContext());

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        name_schedule = loginPreferences.getString("name_schedule", "");

        day_id = getIntent().getIntExtra("day_id", 0);

        nameText = (EditText)findViewById(R.id.etClassName);
        roomText = (EditText)findViewById(R.id.etClassRoom);
        sTimeText = (EditText)findViewById(R.id.etClassSTime);
        eTimeText = (EditText)findViewById(R.id.etClassETime);
        typeText = (EditText)findViewById(R.id.etClassType);

        saveBtn = (Button)findViewById(R.id.btnSave);
        cancelBtn = (Button)findViewById(R.id.btnCancel);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewClassActivity.this.finish();
                Intent intent = new Intent(NewClassActivity.this, ClassesListActivity.class);
                intent.putExtra("day_id", day_id);
                startActivity(intent);
            }
        });

        sTimeText.setText("13:00");
        eTimeText.setText("14:30");
        sTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(true);
            }
        });
        eTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {showTimeDialog(false);
            }
        });
    }

    public void showTimeDialog(boolean _isStart){
        isStart = _isStart;
        new TimePickerDialog(NewClassActivity.this,t,dateAndTime.get(Calendar.HOUR_OF_DAY),dateAndTime.get(Calendar.MINUTE),true).show();

    }

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener(){
        public void onTimeSet(TimePicker view, int _hour, int _minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY,_hour);
            dateAndTime.set(Calendar.MINUTE,_minute);
            String currTime = dateAndTime.get(Calendar.HOUR_OF_DAY)+ ":" + dateAndTime.get(Calendar.MINUTE);
            if(isStart)
                sTimeText.setText(currTime);
            else eTimeText.setText(currTime);
        }
    };

    void save(){
        if (!validate()) {
            onSaveFailed();
            return;
        }
        Lesson lesson = new Lesson(day_id,name,room,stime,etime,type);
        helperDB.addLesson(lesson,name_schedule,day_id);
        NewClassActivity.this.finish();
        Intent intent = new Intent(NewClassActivity.this, ClassesListActivity.class);
        intent.putExtra("day_id", day_id);
        startActivity(intent);

    }
    public boolean validate() {
        boolean valid = true;

        name = nameText.getText().toString();
        room = roomText.getText().toString();
        stime = sTimeText.getText().toString();
        etime = eTimeText.getText().toString();
        type = typeText.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }
        if (stime.isEmpty() ) {
            sTimeText.setError("enter start time");
            valid = false;
        }
        else {
            sTimeText.setError(null);
        }
        if (etime.isEmpty()) {
            eTimeText.setError("enter end time");
            valid = false;
        }

        else {
            eTimeText.setError(null);
        }
        return valid;
    }
    public void onSaveFailed() {
        Toast.makeText(getBaseContext(), "Save class failed", Toast.LENGTH_LONG).show();
        saveBtn.setEnabled(true);
    }



}

