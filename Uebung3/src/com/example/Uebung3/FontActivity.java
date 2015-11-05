package com.example.Uebung3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

/**
 * Created by MrT on 04.11.2015.
 */
public class FontActivity extends Activity implements View.OnClickListener {

    Button btnSmall;
    Button btnMedium;
    Button btnLarge;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font);

        btnSmall = (Button) findViewById(R.id.btnSmall);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnLarge = (Button) findViewById(R.id.btnLarge);

        btnSmall.setOnClickListener(this);
        btnMedium.setOnClickListener(this);
        btnLarge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btnSmall:
                intent.putExtra("font", 10);
                break;
            case R.id.btnMedium:
                intent.putExtra("font", 30);
                break;
            case R.id.btnLarge:
                intent.putExtra("font", 50);
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}