package com.example.julie.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;

import model.Lesson;
import model.RequestTaskSignUp;
import model.Schedule;
import model.User;

public class SignupActivity extends AppCompatActivity {
    Button signUpBtn;
    TextView backText;
    EditText nameText, surnameText,emailText,passwordText,courseText,groupText;
    String name, surname, email, password, course, group;
    String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUpBtn = (Button)findViewById(R.id.btnSignUp);
        backText = (TextView)findViewById(R.id.tvLogin);
        nameText = (EditText)findViewById(R.id.etSignUpName);
        surnameText = (EditText)findViewById(R.id.etSignUpSurname);
        courseText = (EditText)findViewById(R.id.etSignUpCourse);
        groupText = (EditText)findViewById(R.id.etSignUpGroup);
        emailText = (EditText)findViewById(R.id.etSignUpEmail);
        passwordText = (EditText)findViewById(R.id.etSignUpPassword);

        final Intent intent = new Intent(this, LoginActivity.class);

        backText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SignupActivity.this.finish();
                startActivity(intent);
            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    private void signup() {
        Log.d("SignupActivity", "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }
        User user = new User(name,surname,Integer.valueOf(course),Integer.valueOf(group),email,password,new ArrayList<Schedule>());
        ip = getResources().getString(R.string.ip);
        new RequestTaskSignUp(SignupActivity.this, getBaseContext(), user).execute(ip + "/users/signup");
    }


    public void onSignupSuccess() {
        signUpBtn.setEnabled(true);
        setResult(RESULT_OK, null);
        final Intent intentView = new Intent(this,MainActivity.class);
        startActivity(intentView);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
        signUpBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        name = nameText.getText().toString();
        surname = surnameText.getText().toString();
        course = courseText.getText().toString();
        group = groupText.getText().toString();
        email = emailText.getText().toString();
        password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (surname.isEmpty() || name.length() < 3) {
            surnameText.setError("at least 3 characters");
            valid = false;
        } else {
            surnameText.setError(null);
        }
        if (course.isEmpty() ) {
            courseText.setError("enter course");
            valid = false;

        }
        else if (Integer.parseInt(course) > 5){
            courseText.setError("enter right course");
            valid = false;
        }
        else {
            courseText.setError(null);
        }
        if (group.isEmpty()) {
            groupText.setError("enter group");
            valid = false;
        }
        else if (Integer.parseInt(group) > 13){
            courseText.setError("enter right group");
            valid = false;
        }
        else {
            groupText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        SignupActivity.this.finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}