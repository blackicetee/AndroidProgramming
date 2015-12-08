package com.example.Uebung8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tstegemann on 08.12.2015.
 */
public class Edit extends Activity implements View.OnClickListener {

    EditText etUpdateName;
    EditText etUpdateRoomID;
    EditText etUpdateStaticPhoneID;
    EditText etUpdateMobilePhoneID;
    EditText etUpdatePositionID;
    Button btnStartUpdate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        etUpdateName = (EditText) findViewById(R.id.etUpdateName);
        etUpdateRoomID = (EditText) findViewById(R.id.etUpdateRoomID);
        etUpdateStaticPhoneID = (EditText) findViewById(R.id.etUpdateStaticPhoneID);
        etUpdateMobilePhoneID = (EditText) findViewById(R.id.etUpdateMobilePhoneID);
        etUpdatePositionID = (EditText) findViewById(R.id.etUpdatePositionID);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            etUpdateName.setText(extras.getString("UpdateName"));
            etUpdateRoomID.setText(extras.getString("UpdateRoomID"));
            etUpdateStaticPhoneID.setText(extras.getString("UpdateStaticPhoneID"));
            etUpdateMobilePhoneID.setText(extras.getString("UpdateMobilePhoneID"));
            etUpdatePositionID.setText(extras.getString("UpdatePosition"));
        }

        btnStartUpdate = (Button) findViewById(R.id.btnStartUpdate);
        btnStartUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartUpdate:
                if (!etUpdateName.getText().toString().isEmpty() && !etUpdateRoomID.getText().toString().isEmpty() && !etUpdateStaticPhoneID.getText().toString().isEmpty() && !etUpdateMobilePhoneID.getText().toString().isEmpty() && !etUpdatePositionID.getText().toString().isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("UpdateName", etUpdateName.getText().toString());
                    intent.putExtra("UpdateRoomID", etUpdateRoomID.getText().toString());
                    intent.putExtra("UpdateStaticPhoneID", etUpdateStaticPhoneID.getText().toString());
                    intent.putExtra("UpdateMobilePhoneID", etUpdateMobilePhoneID.getText().toString());
                    intent.putExtra("UpdatePosition", etUpdateName.getText().toString());
                    //Toast.makeText(this, intent.getStringExtra("SelectQuery"), Toast.LENGTH_LONG).show();
                    setResult(RESULT_OK, intent);
                } else {
                    Toast.makeText(this, "Do not forget to enter every information! You can`t just leave something empty here", Toast.LENGTH_LONG).show();
                }
                finish();
                break;
        }
    }
}