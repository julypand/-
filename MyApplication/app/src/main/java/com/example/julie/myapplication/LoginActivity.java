package com.example.julie.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.sql.Time;
import java.util.ArrayList;

import model.AES;
import model.HelperDB;
import model.Lesson;


public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView singupText;
    EditText emailText, passwordText;
    CheckBox saveLoginCheckBox;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;
    int group_id = 0;
    HelperDB dbHelper;

    ArrayList<Lesson> lessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.btnLogin);
        singupText = (TextView)findViewById(R.id.tvLoginSingup);
        emailText = (EditText)findViewById(R.id.etLoginEmail);
        passwordText = (EditText)findViewById(R.id.etLoginPassword);
        saveLoginCheckBox = (CheckBox)findViewById(R.id.cbSaveLogin);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefEditor = loginPreferences.edit();

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
        new RequestTask().execute("http://192.168.100.7:8080/users/login");
    }

    private boolean validate(){
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

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
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginBtn.setEnabled(true);
    }

    private void onLoginSuccess(){
        loginBtn.setEnabled(true);
        setResult(RESULT_OK, null);
        LoginActivity.this.finish();
        startActivity(new Intent(this, ViewActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class RequestTask extends AsyncTask<String, Void, Void>{

        ProgressDialog pDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
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

                //json email+password
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("email", email); //3

                //forward TO server
                os = connection.getOutputStream();
                bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write(jsonParam.toString());
                bw.flush();
                bw.close();
                os.close();

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
                //1
                e.printStackTrace();
                error = e.getMessage().toString();
            } catch (IOException e) {
                //2
                e.printStackTrace();
                error = e.getMessage().toString();
            } catch (JSONException e) {
                //3
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
                AES.setKey(password);
                AES.decrypt(parseJSON(msgFromServer).trim());
                if(AES.getDecryptedString().equals(password)){
                    if(saveLoginCheckBox.isChecked()) {
                        loginPrefEditor.putBoolean("saveLogin", true);
                    }
                    else {
                        loginPrefEditor.putBoolean("saveLogin", false);
                    }
                    loginPrefEditor.commit();
                    new RequestTask2().execute("http://192.168.100.7:8080/users/classes");

                }
                else{
                    Toast.makeText(getBaseContext(), "Incorrect e-mail or password. Try again", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        LoginActivity.this.finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private class RequestTask2 extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
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

                //json user
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("group", group_id);

                //forward TO server
                os = connection.getOutputStream();
                bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bw.write(jsonParam.toString());
                bw.flush();
                bw.close();
                os.close();

                //get FROM server
                int responseCode = connection.getResponseCode();
                System.out.println("Response code: " + responseCode);

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
            pDialog.setMessage("Load schedule...");
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
                Toast.makeText(getBaseContext(), "Load Schedule Successfully!", Toast.LENGTH_LONG).show();
                parseJSONLessons(msgFromServer, lessons);
                Intent intent = new Intent(LoginActivity.this, ViewActivity.class);
                LoginActivity.this.finish();
                startActivity(intent);
            }
        }
    }


    private String parseJSON(String msg){
        String result = "";
        try {
            JSONObject child = new JSONObject(msg);
            String password = child.getString("password");
            group_id = child.getInt("group_id");
            result = password;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void parseJSONLessons(String msg, ArrayList<Lesson> less){
        try {
            JSONArray parent = new JSONArray(msg);
            for (int i = 0; i < parent.length(); ++i){
                JSONObject child = parent.getJSONObject(i);
                String day = child.getString("day");
                String name = child.getString("name");
                String room = child.getString("room");
                Time timeStart = Time.valueOf(child.getString("timeStart"));
                Time timeEnd = Time.valueOf(child.getString("timeEnd"));
                String type =child.getString("type");
                less.add(new Lesson(day,name, room, timeStart, timeEnd, type));
            }
            dbHelper = new HelperDB(getApplicationContext(),"schedule",null,1);
            dbHelper.addToLocalDB(less);
            dbHelper.close();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onStart() {
        super.onStart();
    }

}
