package com.example.TechnikMobielerSystemeApp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText(R.string.hello);

        final Button button1 = (Button) findViewById(R.id.button1);
        button1.setText(R.string.clickMe);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView1.getText().equals(getString(R.string.hello))) {
                    textView1.setText(R.string.goodNight);
                } else if (textView1.getText().equals(getString(R.string.goodNight))) {
                    textView1.setText(R.string.hello);
                }
            }
        });
    }
}
