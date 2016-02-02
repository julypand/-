package com.example.julie.myapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Lesson;

public class ListClassesActivity extends AppCompatActivity {
    TableLayout tableClasses;
    String day;
    TextView today;
    int group, day_id;
    ArrayList<Lesson> lessons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_classes);

        day = getIntent().getStringExtra("day");
        group = getIntent().getIntExtra("group", 0);
        //day_id = getIntent().getIntExtra("day_id", Integer.parseInt(day.substring(0,1)));

        tableClasses = (TableLayout) this.findViewById( R.id.tableClasses);
        today = (TextView)findViewById(R.id.tvDay);
        today.setText(day);
        new RequestTask().execute("http://192.168.100.7:8080/users/classes");
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
                jsonParam.put("day", day);
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
               Toast.makeText(getBaseContext(), "Ok", Toast.LENGTH_LONG).show();
                ArrayList<Lesson> lessons = new ArrayList<>();
                parseJSON(msgFromServer,lessons);
                for(Lesson les : lessons)
                    addLesson(les);
            }
        }
    }
    private void parseJSON(String msg, ArrayList<Lesson> less){
        try {
            JSONArray parent = new JSONArray(msg);
            for (int i = 0; i < parent.length(); ++i){
                JSONObject child = parent.getJSONObject(i);
                String name = child.getString("name");
                String room = child.getString("room");
                Time timeStart = Time.valueOf(child.getString("timeStart"));
                Time timeEnd = Time.valueOf(child.getString("timeEnd"));
                less.add(new Lesson(name, room, timeStart, timeEnd));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onStart() {
        super.onStart();
    }


    void addLesson(Lesson les){
        TableRow row = new TableRow(new ContextThemeWrapper(ListClassesActivity.this,R.style.Table));
        TextView timeText = new TextView(new ContextThemeWrapper(ListClassesActivity.this,R.style.CellSchedule));
        TextView nameText = new TextView(new ContextThemeWrapper(ListClassesActivity.this,R.style.CellSchedule));
        TextView roomText = new TextView(new ContextThemeWrapper(ListClassesActivity.this,R.style.CellSchedule));
        SimpleDateFormat format=new SimpleDateFormat("HH:mm");
        timeText.setText(format.format(les.getTimeStart()) + " - " + format.format(les.getTimeEnd()));
        nameText.setText(les.getName());
        roomText.setText(les.getRoom());
        row.addView(timeText);
        row.addView(nameText);
        row.addView(roomText);
        tableClasses.addView(row);
    }





}
