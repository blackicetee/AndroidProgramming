package com.example.Uebung11BroadcastReceiver;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    public final static String PARAM_TIME = "time";
    public final static String PARAM_TASK = "task";
    public final static String PARAM_RESULT = "result";
    public final static String BROADCAST_ACTION = "com.example.Uebung11BroadcastReceiver.s0539757.htw-berlin.de";
    private final static String LOG_TAG = "mainActivityLogs:";
    private List<String> finishedTasksInTheLast60Sec = new ArrayList<>();
    private int taskCode = 0;
    Button btnStartService;
    ListView listView;
    BroadcastReceiver br;

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
        unregisterReceiver(br);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btnStartService = (Button) findViewById(R.id.btnStartService);
        listView = (ListView) findViewById(R.id.listView);

        btnStartService.setOnClickListener(this);
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finishedTasksInTheLast60Sec = intent.getStringArrayListExtra(PARAM_RESULT);
                insertListInListView(finishedTasksInTheLast60Sec);
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intentFilter);
    }

    public void onClickStart(View v) {
        Log.d(LOG_TAG, "MainActivity onClickStart");
        taskCode += 4;
        startMyService(7, taskCode - 3);
        startMyService(3, taskCode - 2);
        startMyService(6, taskCode - 1);
        startMyService(9, taskCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                onClickStart(v);
                break;
        }
    }

    private void startMyService(int time, int taskCode) {
        Intent intent = new Intent(this, MyService.class).putExtra(PARAM_TIME, time);
        intent.putExtra(PARAM_TASK, taskCode);
        startService(intent);
    }

    private void insertListInListView(List<String> l) {
        if (l.size() > 0) {
            String[] stringArray = l.toArray(new String[l.size()]);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<>(this,
                            android.R.layout.simple_list_item_1, stringArray);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "List is empty!", Toast.LENGTH_LONG).show();
            listView.setAdapter(null);
        }
    }
}
