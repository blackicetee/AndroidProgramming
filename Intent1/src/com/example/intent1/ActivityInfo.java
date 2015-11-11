package com.example.intent1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MrT on 11.11.2015.
 */
public class ActivityInfo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        Intent intent = getIntent();

        String action = intent.getAction();
        String format = "";
        String textInfo = "";

        if (action.equals("intent.action.showTime")) {
            format = "HH:mm:ss";
            textInfo = "Time: ";
        }
        else if (action.equals("intent.action.showDate")) {
            format = "dd.MM.yyyy";
            textInfo = "Date: ";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String datetime = sdf.format(new Date(System.currentTimeMillis()));

        TextView tvInfo = (TextView) findViewById(R.id.tvInfo);
        tvInfo.setText(textInfo + datetime);
    }
}