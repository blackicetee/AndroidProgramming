package com.example.Uebung7;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by MrT on 25.11.2015.
 */
public class Alarm extends Activity implements View.OnClickListener {
    EditText etAlarmMassage;
    EditText etAlarmHours;
    EditText etAlarmMinutes;
    Button btnActivateAlarm;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        etAlarmMassage = (EditText) findViewById(R.id.etAlarmMassage);
        etAlarmHours = (EditText) findViewById(R.id.etAlarmHours);
        etAlarmMinutes = (EditText) findViewById(R.id.etAlarmMinutes);
        btnActivateAlarm = (Button) findViewById(R.id.btnActivateAlarm);

        etAlarmMassage.setOnClickListener(this);
        etAlarmHours.setOnClickListener(this);
        etAlarmMinutes.setOnClickListener(this);

        btnActivateAlarm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActivateAlarm:
                String massage = "Default Alarm";
                String hours = "0";
                String minutes = "0";
                if (!etAlarmMassage.getText().toString().isEmpty()) {
                    massage =  etAlarmMassage.getText().toString();
                }
                if (!etAlarmHours.getText().toString().isEmpty()) {
                    hours = etAlarmHours.getText().toString();
                }
                if (!etAlarmMinutes.getText().toString().isEmpty()) {
                    minutes = etAlarmMinutes.getText().toString();
                }
                if (!(etAlarmMassage.getText().toString().isEmpty() && etAlarmHours.getText().toString().isEmpty() && etAlarmMinutes.getText().toString().isEmpty())) {
                    Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM)
                        .putExtra(AlarmClock.EXTRA_MESSAGE, massage)
                        .putExtra(AlarmClock.EXTRA_HOUR, hours)
                        .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
                    if (alarmIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(alarmIntent);
                    }
                }
                finish();
                break;
        }
    }
}