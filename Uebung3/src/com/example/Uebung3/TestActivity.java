package com.example.Uebung3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by MrT on 04.11.2015.
 */
public class TestActivity extends Activity implements View.OnClickListener {

    Button btnIsNum;
    Button btnIsEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        btnIsNum = (Button) findViewById(R.id.btnIsNum);
        btnIsEmpty = (Button) findViewById(R.id.btnIsEmpty);

        btnIsNum.setOnClickListener(this);
        btnIsEmpty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}