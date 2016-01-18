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

import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity implements View.OnClickListener {
    final String LOG_TAG = "mainActivityLogs";
    Button btnStartService;

    private int finishedTasks = 0;
    private int startedTasks = 0;
    private ProgressBar pbService;
    private Handler progressHandler = new Handler();

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finishedTasks = intent.getIntExtra("stoppedTasks", 0);
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
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "MainActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnStartService = (Button) findViewById(R.id.btnStartService);
        pbService = (ProgressBar) findViewById(R.id.pbService);

        btnStartService.setOnClickListener(this);

        new Thread(new Runnable() {
            public void run() {
                while (finishedTasks < 100) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressHandler.post(new Runnable() {
                        public void run() {
                            pbService.setProgress(finishedTasks);
                        }
                    });
                }
            }
        }).start();
    }

    public void onClickStart(View v) {
        Log.d(LOG_TAG, "MainActivity onClickStart");
        startService(new Intent(this, MyService.class).putExtra("time", 7));
        startService(new Intent(this, MyService.class).putExtra("time", 2));
        startService(new Intent(this, MyService.class).putExtra("time", 4));
        startService(new Intent(this, MyService.class).putExtra("time", 10));
        startedTasks += 4;
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
