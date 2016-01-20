package com.example.Uebung11PendingIntent;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

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
    Handler h = new Handler();
    List<String> finishedTasksInTheLast60Sec = new ArrayList<>();
    ExecutorService es;
    PendingIntent pi;
    Runnable r = new Runnable() {
        @Override
        public void run() {
            sendPendingIntent();
            h.postDelayed(this, 60000);
            finishedTasksInTheLast60Sec = new ArrayList<>();
        }
    };

    private void sendPendingIntent() {
        Intent intent = new Intent();
        intent.putExtra(MainActivity.PARAM_RESULT, (ArrayList<String>) finishedTasksInTheLast60Sec);
        try {
            pi.send(MyService.this, MainActivity.STATUS_FINISH, intent);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
        es = Executors.newFixedThreadPool(1);
        h.postDelayed(r, 60000);
    }

    public void onDestroy() {
        super.onDestroy();
        h.removeCallbacks(r);
        es.shutdownNow();
        Log.d(LOG_TAG, "MyService onDestroy ");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");
        readFlags(flags);
        int time = intent.getIntExtra(MainActivity.PARAM_TIME, 1);
        pi = intent.getParcelableExtra(MainActivity.PARAM_PINTENT);
        MyRun mr = new MyRun(time, startId);
        es.execute(mr);
        return START_NOT_STICKY;
        //return START_STICKY;
        //return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "MyService onBind");
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
            finishedTasksInTheLast60Sec.add("Finished Task: MyRun#" + startId);
            Log.d(LOG_TAG, "MyRun#" + startId + " end)");
        }
    }
}
