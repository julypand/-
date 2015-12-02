package com.example.julie.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    TextView singupText;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.btnLogin);
        singupText = (TextView)findViewById(R.id.tvLoginSingup);
        final Intent intent = new Intent(this, SignupActivity.class);

        View.OnClickListener lst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //через inherit
                //setContentView(R.layout.activity_main);
            }
        };



        singupText.setOnClickListener(lst);
        singupText.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){
            startActivity(intent);
        }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
