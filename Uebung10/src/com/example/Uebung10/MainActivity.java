package com.example.Uebung10;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity implements View.OnClickListener {
    final String LOG_TAG = "mainActivityLogs";
    Button btnStartService;

    private int numberOfFinishedTasks = 0;
    private List<String> finishedTasksInTheLast60Sec = new ArrayList<>();
    private int startedTasks = 0;
    private ProgressBar pbService;
    private Handler progressHandler = new Handler();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finishedTasksInTheLast60Sec = intent.getStringArrayListExtra("finishedTasks");
            if (finishedTasksInTheLast60Sec != null) {
                numberOfFinishedTasks += finishedTasksInTheLast60Sec.size();
                for (String finishedTask : finishedTasksInTheLast60Sec) {
                    Log.d(LOG_TAG, finishedTask);
                }
            } else Toast.makeText(getApplicationContext(), "You need to start new services if you want to log more finished tasks!", Toast.LENGTH_LONG).show();
        }
    };

    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("myServiceUpdate"));
    }

    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "MainActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnStartService = (Button) findViewById(R.id.btnStartService);
        pbService = (ProgressBar) findViewById(R.id.pbService);

        btnStartService.setOnClickListener(this);
    }

    public void onClickStart(View v) {
        Log.d(LOG_TAG, "MainActivity onClickStart");
        startService(new Intent(this, MyService.class).putExtra("time", 7));
        startService(new Intent(this, MyService.class).putExtra("time", 2));
        startService(new Intent(this, MyService.class).putExtra("time", 4));
        startService(new Intent(this, MyService.class).putExtra("time", 10));
        startedTasks += 4;
        pbService.setMax(startedTasks);
        Log.d(LOG_TAG, "New max progress bar: " + startedTasks);
        processProgressBar();
    }

    private void processProgressBar() {
        new Thread(new Runnable() {
            public void run() {
                while (numberOfFinishedTasks < startedTasks) {
                    postProgress(numberOfFinishedTasks);
                    holdProgressFor(1);
                }
                postProgress(numberOfFinishedTasks);
            }
        }).start();
    }

    private void postProgress(int progress) {
        progressHandler.post(new Runnable() {
            @Override
            public void run() {
                pbService.setProgress(progress);
                //Log.d(LOG_TAG, "Progress: " + progressStatus + "/" + pbService.getMax());
            }
        });
    }

    private void holdProgressFor(int timeInSec) {
        try {
            TimeUnit.SECONDS.sleep(timeInSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                onClickStart(v);
                break;
        }
    }
}
