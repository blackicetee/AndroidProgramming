package com.example.Uebung12;

import android.app.Activity;
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
    TextView textView;
    SensorManager sensorManager;
    List<Sensor> sensorList;
    Sensor sensorLight;
    Button btnSensor;
    Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView = (TextView) findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        btnSensor = (Button) findViewById(R.id.btnSensor);
        btnSensor.setOnClickListener(this);

    }

    public void onClickSensorList(View v) {
        sensorManager.unregisterListener(listenerLight, sensorLight);
        StringBuilder sb = new StringBuilder();

        for (Sensor sensor : sensorList) {
            sb.append("name = ").append(sensor.getName()).append(", type = ")
                    .append(sensor.getType()).append("\nvendor = ")
                    .append(sensor.getVendor()).append(", version = ")
                    .append(sensor.getVersion()).append("\nmax = ")
                    .append(sensor.getMaximumRange()).append(", resolution = ")
                    .append(sensor.getResolution())
                    .append("\n-----------------------------------------------\n");
        }
        textView.setText(sb);
    }

    public void onClickSensorLight(View v) {
        sensorManager.registerListener(listenerLight, sensorLight, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorLight);
    }

    SensorEventListener listenerLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            textView.setText(String.valueOf(event.values[0]));
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSensor:
                onClickSensorLight(v);
                //Handler.postDelayed called onClickSensorList(View v) after 7000 milliseconds
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        onClickSensorList(v);
                    }
                }, 7000);
                break;
        }
    }
}
