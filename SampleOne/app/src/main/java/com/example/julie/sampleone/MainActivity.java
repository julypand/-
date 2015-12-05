package com.example.julie.sampleone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//useLibrary 'org.apache.http.legacy'

public class MainActivity extends AppCompatActivity {

    Button button;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //first
                /*Thread thread = new Thread(new Client());
                thread.start();*/
                //second
                new RequestTask().execute("http://www.happyhours.by/api/v1/actions/old/");//("http://192.168.1.5:8080/books");//("http://10.0.2.2:8080/user");///("http://www.androidexample.com/media/webservice/JsonReturn.php"); //("D:\\kursach\\ServerOne\\login.php");
                //third
            }
        });
    }

    private class RequestTask extends AsyncTask<String, Void, Void> {

        //third
        final HttpClient httpClient = new DefaultHttpClient();
        String content;
        String error;
        ProgressDialog pDialog = new ProgressDialog(MainActivity.this);
        String data = "";
        TextView serverDataReceived = (TextView) findViewById(R.id.serverDataReceived);
        TextView showParsedJSON = (TextView) findViewById(R.id.showParsedJSON);
        EditText userinput = (EditText) findViewById(R.id.userinput);

        @Override
        protected void onPreExecute() {

            pDialog.setTitle("Please, wait..");
            /*dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Загружаюсь...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);*/
            pDialog.show();

            try {
                data += "&" + URLEncoder.encode("data", "UTF-8") + userinput.getText();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            //second
            /*try {
                //создаем запрос на сервер
                DefaultHttpClient hc = new DefaultHttpClient();
                ResponseHandler<String> res = new BasicResponseHandler();
                //он у нас будет посылать post запрос
                HttpPost postMethod = new HttpPost(params[0]);
                //будем передавать два параметра
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                //передаем параметры из наших текстбоксов
                //лоигн
                nameValuePairs.add(new BasicNameValuePair("login", "julie"));
                //пароль
                nameValuePairs.add(new BasicNameValuePair("pass", "password"));
                //собераем их вместе и посылаем на сервер
                postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                //получаем ответ от сервера
                String response = hc.execute(postMethod, res);
                //посылаем на вторую активность полученные параметры
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                //то что куда мы будем передавать и что, putExtra(куда, что);
                intent.putExtra(SecondActivity.JsonURL, response.toString());
                startActivity(intent);
            } catch (Exception e) {
                System.out.println("Exp=" + e);
            }*/

            BufferedReader br = null;
            URL url = null;
            try {
                url = new URL(params[0]);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);

                OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
                outputStream.write(data);
                outputStream.flush();

                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = br.readLine())!=null){
                    sb.append(line);
                    sb.append(System.getProperty("line.separator"));
                }
                content = sb.toString();

            } catch (MalformedURLException e) {
                error = e.getMessage();
                e.printStackTrace();
            } catch (IOException e) {
                error = e.getMessage();
                e.printStackTrace();
            } finally{
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if(error != null){
                serverDataReceived.setText("Error: " + error);
            }
            else{
                serverDataReceived.setText(content);

                String output = "";
                JSONObject jsonResponse;

                try {
                    jsonResponse = new JSONObject(content);
                    JSONArray jsonArray = jsonResponse.optJSONArray("Android");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);

                        String name = child.getString("name");
                        String number = child.getString("number");
                        String time = child.getString("date_added");

                        output = "Name = " + name + ", number = " + number + ", time = " + time;

                    }

                    showParsedJSON.setText(output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
