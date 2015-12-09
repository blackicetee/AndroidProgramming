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
                Intent intent = new Intent();
                if (!etUpdateName.getText().toString().isEmpty())
                    intent.putExtra("UpdateName", etUpdateName.getText().toString());
                else
                    intent.putExtra("UpdateName", "");
                if (!etUpdateRoomID.getText().toString().isEmpty())
                    intent.putExtra("UpdateRoomID", etUpdateRoomID.getText().toString());
                else
                    intent.putExtra("UpdateRoomID", "");
                if (!etUpdateStaticPhoneID.getText().toString().isEmpty())
                    intent.putExtra("UpdateStaticPhoneID", etUpdateStaticPhoneID.getText().toString());
                else
                    intent.putExtra("UpdateStaticPhoneID", "");
                if (!etUpdateMobilePhoneID.getText().toString().isEmpty())
                    intent.putExtra("UpdateMobilePhoneID", etUpdateMobilePhoneID.getText().toString());
                else
                    intent.putExtra("UpdateMobilePhoneID", "");
                if (!etUpdatePositionID.getText().toString().isEmpty())
                    intent.putExtra("UpdatePositionID", etUpdatePositionID.getText().toString());
                else
                    intent.putExtra("UpdatePositionID", "");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }
}