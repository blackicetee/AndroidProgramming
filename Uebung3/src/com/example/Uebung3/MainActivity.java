package com.example.Uebung3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    EditText etPlain;
    Button btnColor;
    Button btnAlignment;
    Button btnFont;
    Button btnTest;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //find Views
        etPlain = (EditText) findViewById(R.id.etPlain);
        btnColor = (Button) findViewById(R.id.btnColor);
        btnAlignment = (Button) findViewById(R.id.btnAlignment);
        btnFont = (Button) findViewById(R.id.btnFont);
        btnTest = (Button) findViewById(R.id.btnTest);

        //define listener
        btnColor.setOnClickListener(this);
        btnAlignment.setOnClickListener(this);
        btnFont.setOnClickListener(this);
        btnTest.setOnClickListener(this);
    }

    final int REQUEST_CODE_COLOR = 1;
    final int REQUEST_CODE_ALIGNMENT = 2;
    final int REQUEST_CODE_FONT = 3;
    final int REQUEST_CODE_TEST = 4;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnColor:
                intent = new Intent(this, ColorActivity.class);
                startActivityForResult(intent, REQUEST_CODE_COLOR);
                break;
            case R.id.btnAlignment:
                intent = new Intent(this, AlignmentActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ALIGNMENT);
                break;
            case R.id.btnFont:
                intent = new Intent(this, FontActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FONT);
                break;
            case R.id.btnTest:
                intent = new Intent(this, TestActivity.class);
                startActivityForResult(intent, REQUEST_CODE_TEST);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
    }
}
