package com.example.julie.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import model.AES;

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

        new RequestTask().execute("http://192.168.1.2:8080/users/signup");
    }


    public void onSignupSuccess() {
        signUpBtn.setEnabled(true);
        setResult(RESULT_OK, null);
        final Intent intentView = new Intent(this,ViewActivity.class);
        startActivity(intentView);
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
        else if (Integer.parseInt(group) > 4){
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

    private class RequestTask extends AsyncTask<String, Void, Void>{

        ProgressDialog pDialog = new ProgressDialog(SignupActivity.this, R.style.AppTheme);
        String name = nameText.getText().toString();
        String surname = surnameText.getText().toString();
        String course = courseText.getText().toString();
        String group = groupText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String error = null;
        String msgFromServer = null;

        @Override
        protected Void doInBackground(String... params) {
            BufferedReader br = null;
            OutputStream os = null;
            BufferedWriter bw = null;
            StringBuilder sb = new StringBuilder();
            URL url = null;

            try{
                url = new URL(params[0]); //1
                HttpURLConnection connection = (HttpURLConnection)url.openConnection(); //2
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.connect();

                //encrypt password
                AES.setKey(password);
                AES.encrypt(password.trim());

                //json user
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("name", name);
                jsonParam.put("surname", surname);
                jsonParam.put("course", course);
                jsonParam.put("group", group);
                jsonParam.put("email", email);
                jsonParam.put("password", AES.getEncryptedString());

                //forward TO server
                os = connection.getOutputStream();
                bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write(jsonParam.toString());
                bw.flush();
                bw.close();
                os.close();

                //get FROM server
                /*int responseCode = connection.getResponseCode();
                System.out.println("Response code: " + responseCode);*/

                connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = null;
                while((line = br.readLine()) != null){
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }
                msgFromServer = sb.toString();
                br.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                error = e.getMessage().toString();
            } catch (ProtocolException e) {
                e.printStackTrace();
                error = e.getMessage().toString();
            } catch (IOException e) {
                e.printStackTrace();
                error = e.getMessage().toString();
            } catch (JSONException e) {
                e.printStackTrace();
                error = e.getMessage().toString();
            }
            return null;
        }

        @Override
        protected void onPreExecute(){
            pDialog.setTitle("Please, wait..");
            pDialog.setIndeterminate(true);
            pDialog.setMessage("Login...");
            pDialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            pDialog.dismiss();

            if(error != null){
                Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG).show();
            }
            else{
                if(parseJSON(msgFromServer)){
                    Toast.makeText(getBaseContext(), "Successful signing!", Toast.LENGTH_LONG).show();
                    SignupActivity.this.finish();
                    startActivity(new Intent(SignupActivity.this, ViewActivity.class));
                }
                else{
                    Toast.makeText(getBaseContext(), "Someboby has that e-mail. Change it.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private boolean parseJSON(String msg){
        try {
            JSONObject child = new JSONObject(msg);
            String email = child.getString("email");
            if (email.equals("null"))
                return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        SignupActivity.this.finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}