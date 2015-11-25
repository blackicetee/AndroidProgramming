package com.example.Uebung7;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Intents;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tstegemann on 24.11.2015.
 */
public class EditContact2 extends Activity implements View.OnClickListener {
    EditText etContactName;
    EditText etContactNumber;
    EditText etContactEmail;
    Button btnEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editcontact2);

        etContactName = (EditText) findViewById(R.id.etContactName);
        etContactNumber = (EditText) findViewById(R.id.etContactNumber);
        etContactEmail = (EditText) findViewById(R.id.etContactEmail);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        etContactName.setOnClickListener(this);
        etContactNumber.setOnClickListener(this);
        etContactEmail.setOnClickListener(this);

        btnEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEdit:
                Uri contact2Uri = Uri.parse("content://contacts/people/2");
                Intent editIntent = new Intent(Intent.ACTION_EDIT, contact2Uri);
                if (!etContactName.getText().toString().isEmpty()) {
                    editIntent.putExtra(ContactsContract.Intents.Insert.NAME, etContactName.getText().toString());
                }
                if (!etContactNumber.getText().toString().isEmpty()) {
                    editIntent.putExtra(ContactsContract.Intents.Insert.PHONE, etContactNumber.getText().toString());
                }
                if (!etContactEmail.getText().toString().isEmpty()) {
                    editIntent.putExtra(ContactsContract.Intents.Insert.EMAIL, etContactEmail.getText().toString());
                }
                if (!(etContactName.getText().toString().isEmpty() && etContactNumber.getText().toString().isEmpty() && etContactEmail.getText().toString().isEmpty())) {
                    startActivity(editIntent);
                }
                finish();
                break;
        }
    }
}