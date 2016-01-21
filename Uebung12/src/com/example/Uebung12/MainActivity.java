package com.example.Uebung12;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements View.OnClickListener {

    private final String LOG_TAG = "MainActivityLog";
    public final static String PARAM_RESULT = "serviceResult";
    public final static String BROADCAST_ACTION = "com.example.Uebung12Sensor.s0539757.htw-berlin.de";
    TextView textView;
    Button btnActivateSensor;
    Button btnDeactivateSensor;
    BroadcastReceiver br;

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
        stopService(new Intent(this, SensorService.class));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView = (TextView) findViewById(R.id.textView);
        btnActivateSensor = (Button) findViewById(R.id.btnActivateSensor);
        btnDeactivateSensor = (Button) findViewById(R.id.btnDeactivateSensor);
        btnActivateSensor.setOnClickListener(this);
        btnDeactivateSensor.setOnClickListener(this);

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                textView.setText(intent.getStringExtra(PARAM_RESULT));
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnActivateSensor:
                startService(new Intent(this, SensorService.class));
                break;
            case R.id.btnDeactivateSensor:
                finish();
                break;
        }
    }
}
