package com.example.Uebung7;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Jason on 26.11.2015.
 */
public class Email extends Activity implements View.OnClickListener {
    EditText etEmailAddresses;
    EditText etSubject;
    EditText etAttachments;
    Button btnSendEmail;

    public void composeEmail(String[] addresses, String subject, Uri attachments) {
        Intent intent = new Intent(Intent.ACTION_SEND)
                .setType("*/*")
                .putExtra(Intent.EXTRA_EMAIL, addresses)
                .putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_STREAM, attachments);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);

        etEmailAddresses = (EditText) findViewById(R.id.etEmailAddresses);
        etSubject = (EditText) findViewById(R.id.etSubject);
        etAttachments = (EditText) findViewById(R.id.etAttachments);
        btnSendEmail = (Button) findViewById(R.id.btnSendEmail);

        etEmailAddresses.setOnClickListener(this);
        etSubject.setOnClickListener(this);
        etAttachments.setOnClickListener(this);

        btnSendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendEmail:
                String[] addresses = null;
                String subject = "";
                Uri attachments = null;
                if (!etEmailAddresses.getText().toString().isEmpty()) {
                    addresses = etEmailAddresses.getText().toString().split(" ");
                }
                if (!etSubject.getText().toString().isEmpty()) {
                    subject = etSubject.getText().toString();
                }
                if (!etAttachments.getText().toString().isEmpty()) {
                    attachments = Uri.parse(etAttachments.getText().toString());
                }
                composeEmail(addresses, subject, attachments);
                finish();
                break;
        }
    }
}