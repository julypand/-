package com.example.julie.sampleone;

import android.content.Intent;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import 	java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//useLibrary 'org.apache.http.legacy'

/**
 * Created by Julie on 21.11.2015.
 */
public class RequestTaskTwo extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {
        try{
            /*URL url = new URL("http://www.android.com/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();*/
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
            System.out.println(response.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
