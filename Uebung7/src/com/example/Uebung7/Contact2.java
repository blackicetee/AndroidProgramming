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
public class Contact2 extends Activity implements View.OnClickListener {

    Button btnEditContact;
    Button btnShowContact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact2);


        btnEditContact = (Button) findViewById(R.id.btnEditContact);
        btnShowContact = (Button) findViewById(R.id.btnShowContact);

        btnEditContact.setOnClickListener(this);
        btnShowContact.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEditContact:
                Intent editContactIntent = new Intent("htw.berlin.s0539757.intent.action.editContact2");
                startActivity(editContactIntent);
                break;
            case R.id.btnShowContact:
                Uri contact2Uri = Uri.parse("content://contacts/people/2");
                Intent contact2Intent = new Intent(Intent.ACTION_VIEW, contact2Uri);
                startActivity(contact2Intent);
                break;
        }
    }
}