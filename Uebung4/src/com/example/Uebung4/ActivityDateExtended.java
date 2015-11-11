package com.example.Uebung4;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.Uebung4.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MrT on 11.11.2015.
 */
public class ActivityDateExtended extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd, yyyy");
        String time = sdf.format(new Date(System.currentTimeMillis()));

        TextView tvTime = (TextView) findViewById(R.id.tvDate);
        tvTime.setText(time);
    }
}