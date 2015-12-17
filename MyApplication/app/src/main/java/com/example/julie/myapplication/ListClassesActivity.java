package com.example.julie.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import model.Lesson;

public class ListClassesActivity extends AppCompatActivity {
    TableLayout tableClasses;
    String day;
    TextView today;
    int group = 0, day_id = 0;
    //ArrayList<Lesson> lessons = new ArrayList<Lesson>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_classes);

        day = getIntent().getStringExtra("day");
        group = getIntent().getIntExtra("group", 0);
        day_id = getIntent().getIntExtra("day_id", 0);

        System.out.println("LIST CLASSES GROUP: " + group + ", " + day_id);

        tableClasses = (TableLayout) this.findViewById( R.id.tableClasses);
        today = (TextView)findViewById(R.id.tvDay);
        today.setText(day);

        new RequestTask().execute("http://192.168.0.136:8080/users/classes");
    }

    private class RequestTask extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog = new ProgressDialog(ListClassesActivity.this, R.style.AppTheme);
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
                jsonParam.put("day", day_id);
                jsonParam.put("group", group);

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
                System.out.println(msgFromServer);
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
            pDialog.setMessage("Load...");
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
                System.out.println("VIEW: " + parseJSON(msgFromServer));
                Toast.makeText(getBaseContext(), "message", Toast.LENGTH_LONG).show();
                //final Intent intent = new Intent(ViewActivity.this, ListClassesActivity.class);
                //intent.putExtra("day", btnText);
                //intent.putExtra("lessons", parseJSON(msgFromServer));
                //startActivity(intent);
            }
        }
    }

    private ArrayList<Lesson> parseJSON(String msg){
        ArrayList<Lesson> list = new ArrayList<Lesson>();

        try {
            JSONArray parent = new JSONArray(msg);
            for (int i = 0; i < parent.length(); ++i){
                JSONObject child = parent.getJSONObject(i);
                String name = child.getString("name");
                String room = child.getString("room");
                Time timeStart = Time.valueOf(child.getString("timeStart")); //////////MAY BE AN ERROR
                Time timeEnd = Time.valueOf(child.getString("timeEnd"));
                list.add(new Lesson(name, room, timeStart, timeEnd));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void onStart() {
        super.onStart();
    }



}
