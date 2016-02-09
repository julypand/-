package model;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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

public class RequestTaskClasses extends AsyncTask<String, Void, Void> {

    private ProgressDialog pDialog;
    private int group;
    private Context context;
    HelperDB dbHelper;
    String error = null;
    String msgFromServer = null;
    ArrayList<Lesson> lessons = new ArrayList<>();

    public RequestTaskClasses(Context context, ProgressDialog pDialog, int group){
        this.setpDialog(pDialog);
        this.setGroup(group);
        this.setContext(context);
    }

    @Override
    protected Void doInBackground(String... params) {
        BufferedReader br;
        OutputStream os;
        BufferedWriter bw;
        StringBuilder sb = new StringBuilder();
        URL url;

        try{
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(); //2
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            //json user
            JSONObject jsonParam = new JSONObject();
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
        pDialog.setMessage("Load schedule...");
        pDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result){
        super.onPostExecute(result);
        pDialog.dismiss();

        if(error != null){
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(), "Load Schedule Successfully!", Toast.LENGTH_LONG).show();
            parseJSONLessons(msgFromServer, lessons);

        }
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
            dbHelper = new HelperDB(getContext(),"schedule",null,1);
            dbHelper.addToLocalDB(less);
            dbHelper.close();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ProgressDialog getpDialog() {
        return pDialog;
    }

    public void setpDialog(ProgressDialog pDialog) {
        this.pDialog = pDialog;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}


