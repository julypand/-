package model;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.julie.myapplication.R;

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

public class RequestTaskSignUp extends AsyncTask<String, Void, Void> {

    private ProgressDialog pDialog;
    private User user;
    private Activity activity;
    private Context context;
    String error = null;
    String msgFromServer = null;

    public  RequestTaskSignUp(Activity activity, Context context, User user){
        this.setActivity(activity);
        this.setContext(context);
        this.setpDialog();
        this.setUser(user);
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

            //encrypt password
            AES.setKey(user.getPassword());
            AES.encrypt(user.getPassword().trim());

            //json user
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", user.getName());
            jsonParam.put("surname", user.getSurname());
            jsonParam.put("course", user.getCourse());
            jsonParam.put("group", user.getGroup());
            jsonParam.put("email", user.getEmail());
            jsonParam.put("password", AES.getEncryptedString());

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
        pDialog.setTitle(getActivity().getResources().getString(R.string.wait));
        pDialog.setIndeterminate(true);
        pDialog.setMessage(getActivity().getResources().getString(R.string.signup_wait));
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
            if(parseJSON(msgFromServer)){
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.success_signup), Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.change_email), Toast.LENGTH_LONG).show();
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

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProgressDialog getpDialog() {
        return pDialog;
    }

    public void setpDialog() {
        this.pDialog = new ProgressDialog(getActivity(), R.style.AppTheme);
    }
}
