package model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.julie.myapplication.ClassesListActivity;
import com.example.julie.myapplication.R;
import com.fasterxml.jackson.databind.ObjectMapper;

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

public class RequestTaskNewClass extends AsyncTask<String, Void, Void> {
    private int idLesson;
    private ProgressDialog pDialog;
    private Lesson lesson;
    private Context context;
    private Activity activity;
    private HelperDB dbHelper;
    private String error = null;
    private String scheduleName;

    public RequestTaskNewClass(Activity activity,Context context,Lesson lesson,String schedule_name){
        this.setActivity(activity);
        this.setpDialog();
        this.setLesson(lesson);
        this.setContext(context);
        this.setScheduleName(schedule_name);
    }

    @Override
    protected Void doInBackground(String... params) {
        BufferedReader br;
        OutputStream os;
        BufferedWriter bw;
        URL url;

        try{
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(); //2
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            os = connection.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(bw, lesson);
            bw.close();
            os.close();

            connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            idLesson = mapper.readValue(br,int.class);
            lesson.setId(idLesson);
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
        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        pDialog.setTitle(getActivity().getResources().getString(R.string.wait));
        pDialog.setIndeterminate(true);
        pDialog.setMessage(getActivity().getResources().getString(R.string.add_wait));
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
            dbHelper = new HelperDB(getContext());
            dbHelper.addLesson(lesson, scheduleName);
            Intent intent = new Intent(getActivity(), ClassesListActivity.class);
            intent.putExtra("day_id", lesson.getDay());
            getActivity().finish();
            getActivity().startActivity(intent);
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.success_adding), Toast.LENGTH_LONG).show();

        }
    }



    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public ProgressDialog getpDialog() {
        return pDialog;
    }

    public void setpDialog() {
        this.pDialog = new ProgressDialog(getActivity(), R.style.AppTheme);
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}


