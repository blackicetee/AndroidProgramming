package com.example.Uebung12;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SensorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
