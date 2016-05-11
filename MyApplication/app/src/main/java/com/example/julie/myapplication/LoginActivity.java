package com.example.julie.myapplication;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import model.RequestTaskLogin;


public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView singupText;
    EditText emailText, passwordText;
    String email, password;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.btnLogin);
        singupText = (TextView)findViewById(R.id.tvLoginSingup);
        emailText = (EditText)findViewById(R.id.etLoginEmail);
        passwordText = (EditText)findViewById(R.id.etLoginPassword);

        final Intent intent = new Intent(this, SignupActivity.class);

        singupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void login(){
        Log.d("LoginActivity", "Login");
        if(!validate()){
            onLoginFailed();
            return;
        }

        ip =  getResources().getString(R.string.ip);
        new RequestTaskLogin(LoginActivity.this, getBaseContext(), email,password).execute(ip + "/users/login");

    }

    private boolean validate(){
        boolean valid = true;

        email = emailText.getText().toString();
        password = passwordText.getText().toString();

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

    private void onLoginFailed(){
        Toast.makeText(getBaseContext(), getResources().getString(R.string.login_failed), Toast.LENGTH_LONG).show();
        loginBtn.setEnabled(true);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.finish();
        startActivity(intent);
    }


    public void onStart() {
        super.onStart();
    }

}
