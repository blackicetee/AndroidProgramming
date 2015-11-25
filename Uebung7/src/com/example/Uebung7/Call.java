package com.example.Uebung7;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tstegemann on 24.11.2015.
 */
public class Call extends Activity implements View.OnClickListener {
    EditText etDial;
    Button btnDial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call);

        etDial = (EditText) findViewById(R.id.etDial);
        btnDial = (Button) findViewById(R.id.btnDial);

        etDial.setOnClickListener(this);
        btnDial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDial:
                if (!etDial.getText().toString().isEmpty()) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + etDial.getText().toString()));
                    startActivity(callIntent);
                } else {
                    Toast.makeText(this, "Please enter a number!", Toast.LENGTH_LONG).show();
                }
                finish();
                break;
        }
    }
}