package com.example.Uebung3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        Intent intent = getIntent();
        switch (v.getId()) {
            case R.id.btnIsNum:
                try {
                    Double.parseDouble(intent.getStringExtra("EditTextInput"));
                    intent.putExtra("test", "Is a number!");
                } catch (Exception e) {
                    intent.putExtra("test", "Is not a number!");
                }
                break;
            case R.id.btnIsEmpty:
                if (intent.getStringExtra("EditTextInput").equals("")) {
                    intent.putExtra("test", "Is empty!");
                } else {
                    intent.putExtra("test", "Is not empty!");
                }
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}