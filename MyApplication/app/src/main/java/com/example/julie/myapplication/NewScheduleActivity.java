package com.example.julie.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import model.HelperDB;
import model.RequestTaskNewSchedule;

public class NewScheduleActivity extends AppCompatActivity {
    String nameSchedule;
    Button saveBtn, cancelBtn;
    SharedPreferences loginPreferences;
    EditText nameText;
    HelperDB helperDB;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);

        helperDB = new HelperDB(getApplicationContext());

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);


        nameText = (EditText) findViewById(R.id.etScheduleName);

        saveBtn = (Button) findViewById(R.id.btnSave);
        cancelBtn = (Button) findViewById(R.id.btnCancel);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewScheduleActivity.this.finish();
                Intent intent = new Intent(NewScheduleActivity.this, ScheduleListActivity.class);
                startActivity(intent);
            }
        });
    }

    void save() {
        if (!validate()) {
            onSaveFailed();
            return;
        }
        String email = loginPreferences.getString("email", "");
        ip = getResources().getString(R.string.ip);
        new RequestTaskNewSchedule(NewScheduleActivity.this, getBaseContext(), nameSchedule, email).execute(ip + "/users/schedules/add");
    }

    public boolean validate() {
        boolean valid = true;

        nameSchedule = nameText.getText().toString();

        if (nameSchedule.isEmpty() || nameSchedule.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }
        if (helperDB.isNameScheduleExist(nameSchedule)) {
            nameText.setError("this name already exists");
            valid = false;
        }

        return valid;
    }

    public void onSaveFailed() {
        Toast.makeText(getBaseContext(), "Save schedule failed", Toast.LENGTH_LONG).show();
        saveBtn.setEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ScheduleListActivity.class);
        this.finish();
        startActivity(intent);
    }
}