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
import model.RequestTaskEditClass;
import model.RequestTaskLogin;
import model.RequestTaskNewClass;

public class EditClassActivity extends AppCompatActivity {
    int class_id, day_id;
    Lesson lesson;
    String name_schedule;
    Button saveBtn, cancelBtn;
    SharedPreferences loginPreferences;
    EditText nameText, roomText,sTimeText,eTimeText,typeText;
    boolean isStart = true;
    String name, room,stime,etime, type;
    Calendar dateAndTime = Calendar.getInstance();
    HelperDB helperDB;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        class_id = getIntent().getIntExtra("class_id", 0);
        day_id = getIntent().getIntExtra("day_id", 0);
        helperDB = new HelperDB(getApplicationContext());
        lesson = helperDB.getLesson(class_id);

        nameText = (EditText)findViewById(R.id.etClassName);
        roomText = (EditText)findViewById(R.id.etClassRoom);
        sTimeText = (EditText)findViewById(R.id.etClassSTime);
        eTimeText = (EditText)findViewById(R.id.etClassETime);
        typeText = (EditText)findViewById(R.id.etClassType);

        nameText.setText(lesson.getName());
        roomText.setText(lesson.getRoom());
        sTimeText.setText(lesson.convert(lesson.getTimeStart()));
        eTimeText.setText(lesson.convert(lesson.getTimeEnd()));
        typeText.setText(lesson.getType());

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
                EditClassActivity.this.finish();
                Intent intent = new Intent(EditClassActivity.this, ClassesListActivity.class);
                intent.putExtra("day_id", day_id);
                startActivity(intent);
            }
        });

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
        new TimePickerDialog(EditClassActivity.this,t,dateAndTime.get(Calendar.HOUR_OF_DAY),dateAndTime.get(Calendar.MINUTE),true).show();

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
        Lesson lesson = new Lesson(class_id,name,room,stime,etime,type);
        ip =  getResources().getString(R.string.ip);
        new RequestTaskEditClass(EditClassActivity.this, getBaseContext(), lesson).execute(ip + "/users/classes/edit");


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

