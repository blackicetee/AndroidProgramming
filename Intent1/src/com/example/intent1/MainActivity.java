package com.example.intent1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    Button btnShowTime;
    Button btnShowDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnShowTime = (Button) findViewById(R.id.btnShowTime);
        btnShowDate = (Button) findViewById(R.id.btnShowDate);

        btnShowTime.setOnClickListener(this);
        btnShowDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.btnShowTime:
                intent = new Intent("intent.action.showTime");
                startActivity(intent);
                break;
            case R.id.btnShowDate:
                intent = new Intent("intent.action.showDate");
                startActivity(intent);
                break;
        }
    }
}
