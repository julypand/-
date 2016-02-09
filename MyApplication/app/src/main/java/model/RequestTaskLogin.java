package model;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.julie.myapplication.R;
import com.example.julie.myapplication.ViewActivity;

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
import java.net.URL;

public class RequestTaskLogin extends AsyncTask<String, Void, Void> {


    private ProgressDialog pDialog;

    private Context context;
    private Activity activity;
    private String email;
    private String password;
    private int group;
    public SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefEditor;
    private boolean isSaveLogin;
    String error = null;
    String msgFromServer = null;


    public RequestTaskLogin(Activity activity, Context context, ProgressDialog pDialog,String email, String password, boolean isSaveLogin){
        this.setActivity(activity);
        this.setpDialog(pDialog);
        this.setEmail(email);
        this.setPassword(password);
        this.setIsSaveLogin(isSaveLogin);
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
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();

            //json email+password

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("email", getEmail());

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

            AES.setKey(getPassword());
            AES.decrypt(parseJSON(msgFromServer).trim());
            if(AES.getDecryptedString().equals(getPassword())){
                loginPreferences = context.getSharedPreferences("loginPrefs", context.MODE_PRIVATE);
                loginPrefEditor = loginPreferences.edit();
                if(isSaveLogin) {
                    loginPrefEditor.putBoolean("saveLogin", true);
                }
                else {
                    loginPrefEditor.putBoolean("saveLogin", false);
                }
                loginPrefEditor.putInt("group", group);
                loginPrefEditor.commit();
                RequestTaskClasses rtc = new RequestTaskClasses(getActivity(),getContext(),new ProgressDialog(getActivity(), R.style.AppTheme), group);
                rtc.execute(getActivity().getResources().getString(R.string.ip) + "/users/classes");
                Intent intent = new Intent(getActivity(), ViewActivity.class);
                getActivity().finish();
                getActivity().startActivity(intent);
            }
            else{
                Toast.makeText(getContext(), "Incorrect e-mail or password. Try again", Toast.LENGTH_LONG).show();
            }
        }


    private String parseJSON(String msg){
        String result = "";
        try {
            JSONObject child = new JSONObject(msg);
            String password = child.getString("password");
            group= child.getInt("group_id");
            result = password;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setpDialog(ProgressDialog pDialog) {
        this.pDialog = pDialog;
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

    public void setIsSaveLogin(boolean isSaveLogin) {
        this.isSaveLogin = isSaveLogin;
    }
}
