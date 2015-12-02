package com.example.julie.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    Button signUpBtn;
    TextView backText;
    EditText nameText;
    EditText surnameText;
    EditText courseText;
    EditText groupText;
    EditText emailText;
    EditText passwordText;

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

    public void signup() {
        Log.d("SignupActivity", "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signUpBtn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        signUpBtn.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        signUpBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String surname = surnameText.getText().toString();
        String course = courseText.getText().toString();
        String group = groupText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

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
        if (course.isEmpty()) {
            courseText.setError("enter course");
            valid = false;
        } else {
            courseText.setError(null);
        }
        if (group.isEmpty()) {
            groupText.setError("enter group");
            valid = false;
        } else {
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
}
