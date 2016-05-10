package model;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.julie.myapplication.R;
import com.example.julie.myapplication.ScheduleListActivity;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RequestTaskLogin extends AsyncTask<String, Void, Void> {
    private ProgressDialog pDialog;
    private Context context;
    private Activity activity;
    private String email, password;
    private User user;
    public SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;
    String error = null;


    public RequestTaskLogin(Activity activity, Context context, String email, String password){
        this.setActivity(activity);
        this.setpDialog();
        this.setEmail(email);
        this.setPassword(password);
        this.setContext(context);
    }


    @Override
    protected Void doInBackground(String... params) {
        BufferedReader br;
        OutputStream os;
        BufferedWriter bw;
        URL url;

        try{
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            //forward TO server
            os = connection.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            ObjectMapper mapper = new ObjectMapper();

            mapper.writeValue(bw, new User(getEmail()));
            bw.close();
            os.close();


            connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            user = mapper.readValue(br,User.class);
            br.close();


        } catch (MalformedURLException e) {

            e.printStackTrace();
            error = e.getMessage().toString();
        } catch (IOException e) {
            e.printStackTrace();
            error = getActivity().getResources().getString(R.string.offline);
        }

        return null;
    }

    @Override
    protected void onPreExecute(){
        pDialog.setTitle(getActivity().getResources().getString(R.string.wait));
        pDialog.setIndeterminate(true);
        pDialog.setMessage(getActivity().getResources().getString(R.string.login_wait));
        pDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        pDialog.dismiss();
        if (error != null) {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        } else {
            if (user.getPassword() != null) {
                AES.setKey(getPassword());
                AES.decrypt(user.getPassword().trim());
                if (AES.getDecryptedString().equals(getPassword())) {
                    loginPreferences = context.getSharedPreferences("loginPrefs", context.MODE_PRIVATE);
                    loginPrefEditor = loginPreferences.edit();
                    loginPrefEditor.putString("email", email);
                    loginPrefEditor.putBoolean("saveLogin", true);
                    loginPrefEditor.commit();
                    Intent intent = new Intent(getActivity(), ScheduleListActivity.class);
                    getActivity().finish();
                    getActivity().startActivity(intent);
                } else
                    Toast.makeText(getContext(), getActivity().getResources().getString(R.string.incorrent_password), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.incorrent_email), Toast.LENGTH_LONG).show();
        }
    }




    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setpDialog() {
        this.pDialog = new ProgressDialog(getActivity(), R.style.AppTheme);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
