package com.example.julie.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ListClassesActivity extends AppCompatActivity {
    TableLayout tableClasses;
    String day;
    TextView today;
    Button btncreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_classes);

        day = getIntent().getStringExtra("day");
        tableClasses = (TableLayout) this.findViewById( R.id.tableClasses);
        btncreate = (Button)findViewById(R.id.createNewNode);
        today = (TextView)findViewById(R.id.tvDay);
        today.setText(day);

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow row = new TableRow(getApplicationContext());
                row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                for(int i = 0; i < 3; i ++){
                    TextView text = new TextView(getApplicationContext());
                    text.setGravity(Gravity.CENTER);
                    text.setBackgroundColor(Color.WHITE);
                    text.setPadding(1, 1, 1, 1);
                    text.setText("xui");
                    row.addView(text);

                }

                tableClasses.addView(row);
            }
        });

    }
    public void onStart() {
        super.onStart();

    }


}
