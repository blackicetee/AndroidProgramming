package com.example.Uebung7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by MrT on 25.11.2015.
 */
public class TimeTable extends Activity implements View.OnClickListener {
    EditText etTitle;
    EditText etLocation;
    EditText etCalendarStart;
    EditText etCalendarEnd;
    Button btnSetTimeTable;

    public void addEvent(String title, String location, Calendar start, Calendar end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(Events.CONTENT_URI)
                .putExtra(Events.TITLE, title)
                .putExtra(Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etLocation = (EditText) findViewById(R.id.etLocation);
        etCalendarStart = (EditText) findViewById(R.id.etCalendarStart);
        etCalendarEnd = (EditText) findViewById(R.id.etCalendarEnd);
        btnSetTimeTable = (Button) findViewById(R.id.btnSetTimeTable);

        etTitle.setOnClickListener(this);
        etLocation.setOnClickListener(this);
        etCalendarStart.setOnClickListener(this);
        etCalendarEnd.setOnClickListener(this);

        btnSetTimeTable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSetTimeTable:
                String title = "Default Title";
                String location = "Default Location";
                Calendar start = null;
                Calendar end = null;
                if (!etTitle.getText().toString().isEmpty()) {
                    title = etTitle.getText().toString();
                }
                if (!etLocation.getText().toString().isEmpty()) {
                    location = etLocation.getText().toString();
                }
                if (!etCalendarStart.getText().toString().isEmpty()) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd. MMM yyyy, HH:mm:ss", Locale.GERMANY);
                    try {
                        cal.setTime(sdf.parse(etCalendarStart.getText().toString()));
                    } catch (ParseException e) {
                        Toast.makeText(this, "Enter a date like: 14 Mai 2015 16:02:37", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    start = cal;
                }
                if (!etCalendarEnd.getText().toString().isEmpty()) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd. MMM yyyy, HH:mm:ss", Locale.GERMANY);
                    try {
                        cal.setTime(sdf.parse(etCalendarEnd.getText().toString()));
                    } catch (ParseException e) {
                        Toast.makeText(this, "Enter a date like: 14 Mai 2015 16:02:37", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    end = cal;
                }
                addEvent(title, location, start, end);
                finish();
                break;
        }
    }
}