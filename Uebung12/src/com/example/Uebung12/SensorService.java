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
import java.util.Calendar;
import java.util.List;

public class SensorService extends Service {
    private String sensorResult = "";
    final String LOG_TAG = "mySensorServiceLogs";
    SensorManager sensorManager;
    List<Sensor> sensorList;
    List<String> sensorDescriptionList = new ArrayList<>();
    Sensor sensorLight;
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
        intent.putExtra(MainActivity.PARAM_RESULT, (ArrayList<String>) sensorDescriptionList);
        sendBroadcast(intent);
        registerSensorLight();
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
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        registerSensorLight();
        //showAllTypesOfSensors();
        //sensorResult = Integer.toString(Sensor.TYPE_AMBIENT_TEMPERATURE); = 13
        return START_NOT_STICKY;
    }

    public void showAllTypesOfSensors() {
        sensorManager.unregisterListener(listenerLight, sensorLight);
        for (Sensor sensor : sensorList) {
            sensorDescriptionList.add(getInfoAboutSensor(sensor));
        }
    }

    public String getInfoAboutSensor(Sensor sensor) {
        return "name = " + sensor.getName() + ", type = " + sensor.getType() + "\nvendor = " + sensor.getVendor() + ", version = " + sensor.getVersion() + "\nmax = " + sensor.getMaximumRange() + ", resolution = " + sensor.getResolution();
    }


    public void registerSensorLight() {
        sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterSensorLight() {
        sensorManager.unregisterListener(listenerLight);
    }

    SensorEventListener listenerLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minutes = c.get(Calendar.MINUTE);
            int seconds = c.get(Calendar.SECOND);
            sensorDescriptionList.add(0, "Time: " + hour + ":" + minutes + ":" + seconds + " Brightness: " + String.valueOf(event.values[0]) + " lx");
            unregisterSensorLight();
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
