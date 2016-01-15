package com.example.Uebung10;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jason on 14.01.2016.
 */
public class MyService extends Service {
    final String LOG_TAG = "myServiceLogs";
    final Handler h = new Handler();
    List<String> stoppedTasks = new ArrayList<>();
    ExecutorService es;

    private void sendBroadcast(int stoppedTasks) {
        Intent intent = new Intent("myServiceUpdate");
        intent.putExtra("stoppedTasks", stoppedTasks);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(1);
        h.postDelayed(new Runnable() {
            private long time = 0;
            @Override
            public void run() {
                for (String stoppedTask : stoppedTasks) {
                    Log.d(LOG_TAG, stoppedTask);
                }
                time += 60000;
                Log.d(LOG_TAG, "Going for... " + time);
                h.postDelayed(this, 60000);
            }
        }, 60000);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");
        readFlags(flags);
        int time = intent.getIntExtra("time", 1);
        MyRun mr = new MyRun(time, startId);
        es.execute(mr);
        return START_NOT_STICKY;
        //return START_STICKY;
        //return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return null;
    }

    void readFlags(int flags) {
        if ((flags & START_FLAG_REDELIVERY) == START_FLAG_REDELIVERY)
            Log.d(LOG_TAG, "START_FLAG_REDELIVERY");
        if ((flags & START_FLAG_RETRY) == START_FLAG_RETRY)
            Log.d(LOG_TAG, "START_FLAG_RETRY");
    }

    class MyRun implements Runnable {
        int time;
        int startId;

        public MyRun(int time, int startId) {
            this.time = time;
            this.startId = startId;
            Log.d(LOG_TAG, "MyRun#" + startId + " create");
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, "MyRun#" + startId + " start, time = " + time);
            try {
                TimeUnit.SECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        }

        private void stop() {
            stoppedTasks.add("Finished Task: MyRun#" + startId);
            Log.d(LOG_TAG, "MyRun#" + startId + " end, stopSelfResult(" + startId + ") = " + stopSelfResult(startId));
            sendBroadcast(stoppedTasks.size());
        }
    }
}
