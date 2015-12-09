package com.example.Uebung8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Jason on 09.12.2015.
 */
public class Search extends Activity implements View.OnClickListener {

    EditText etSearchName;
    EditText etSearchRoom;
    EditText etSearchPosition;
    Button btnStartSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        etSearchName = (EditText) findViewById(R.id.etSearchName);
        etSearchRoom = (EditText) findViewById(R.id.etSearchRoom);
        etSearchPosition = (EditText) findViewById(R.id.etSearchPosition);

        btnStartSearch = (Button) findViewById(R.id.btnStartSearch);
        btnStartSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartSearch:
                Intent intent = new Intent();
                if (!etSearchName.getText().toString().isEmpty())
                    intent.putExtra("SearchName", etSearchName.getText().toString());
                else
                    intent.putExtra("SearchName", "");
                if (!etSearchRoom.getText().toString().isEmpty())
                    intent.putExtra("SearchRoom", etSearchRoom.getText().toString());
                else
                    intent.putExtra("SearchRoom", "");
                if (!etSearchPosition.getText().toString().isEmpty())
                    intent.putExtra("SearchPosition", etSearchPosition.getText().toString());
                else
                    intent.putExtra("SearchPosition", "");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}