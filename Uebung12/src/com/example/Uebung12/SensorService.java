package com.example.Uebung12;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SensorService extends Service {
    private String sensorResult = "";
    final String LOG_TAG = "mySensorServiceLogs";
    SensorManager sensorManager;
    List<Sensor> sensorList;
    Sensor sensorTemp;
    Handler h = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            sendBroadcastToMain();
            h.postDelayed(this, 5000);
        }
    };

    private void sendBroadcastToMain() {
        Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
        //if (sensorTemp != null) sensorResult = getInfoAboutSensor(sensorTemp);
        intent.putExtra(MainActivity.PARAM_RESULT, sensorResult);
        Log.d(LOG_TAG, intent.getStringExtra(MainActivity.PARAM_RESULT));
        sendBroadcast(intent);
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "SensorService onCreate");
        h.postDelayed(r, 5000);
    }

    public void onDestroy() {
        super.onDestroy();
        h.removeCallbacks(r);
        Log.d(LOG_TAG, "SensorService onDestroy ");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "SensorService onStartCommand");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorTemp = sensorManager.getDefaultSensor(7);
        sensorTemp.getName();
        showAllTypesOfSensors();
        //registerSensorLight();
        //sensorResult = getInfoAboutSensor(sensorTemp);
        return START_NOT_STICKY;
    }

    public void showAllTypesOfSensors() {
        sensorManager.unregisterListener(listenerLight, sensorTemp);
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

        sensorResult = sb.toString();
    }

    public String getInfoAboutSensor(Sensor sensor) {
        return "name = " + sensor.getName() + ", type = " + sensor.getType() + "\nvendor = " + sensor.getVendor() + ", version = " + sensor.getVersion() + "\nmax = " + sensor.getMaximumRange() + ", resolution = " + sensor.getResolution() + "\n-----------------------------------------------\n";
    }


    public void registerSensorLight() {
        sensorManager.registerListener(listenerLight, sensorTemp, SensorManager.SENSOR_DELAY_NORMAL);
    }

    SensorEventListener listenerLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            sensorResult = String.valueOf(event.values[0]);
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
