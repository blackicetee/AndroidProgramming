package com.example.Uebung8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jason on 04.12.2015.
 */
public class Select extends Activity implements View.OnClickListener {
    EditText etSelectPerson;
    Button btnSelectResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        etSelectPerson = (EditText) findViewById(R.id.etSelectPerson);
        btnSelectResult = (Button) findViewById(R.id.btnSelectResult);
        btnSelectResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSelectResult:
                if (!etSelectPerson.getText().toString().isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("SelectQuery", etSelectPerson.getText().toString());
                    //Toast.makeText(this, intent.getStringExtra("SelectQuery"), Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK, intent);

                } else {
                    Toast.makeText(this, "Please enter a search query!", Toast.LENGTH_LONG).show();
                }
                finish();
                break;
        }
    }
}