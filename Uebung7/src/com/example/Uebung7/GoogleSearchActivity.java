package com.example.Uebung7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jason on 22.11.2015.
 */

public class GoogleSearchActivity extends Activity implements View.OnClickListener {

    EditText etGoogleSearch;
    Button btnSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlesearch);

        etGoogleSearch = (EditText) findViewById(R.id.etGoogleSearch);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        etGoogleSearch.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                if (!etGoogleSearch.getText().toString().isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("SearchQuery", etGoogleSearch.getText().toString());
                    setResult(RESULT_OK, intent);
                } else {
                    Toast.makeText(this, "Please enter a search query!", Toast.LENGTH_LONG).show();
                }
                finish();
                break;
        }
    }
}