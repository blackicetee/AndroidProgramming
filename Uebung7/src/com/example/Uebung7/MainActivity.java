package com.example.Uebung7;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    Button btnOpenGoogle;
    Button btnSearchInGoogle;
    Button btnGoogleMapsHTW;
    Button btnContact2;
    Button btnCall;
    Button btnSetAlarm;
    Button btnSetTerm;
    Button btnEmail;
    Button btnSMS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        btnOpenGoogle = (Button) findViewById(R.id.btnOpenGoogle);
        btnSearchInGoogle = (Button) findViewById(R.id.btnSearchInGoogle);
        btnGoogleMapsHTW = (Button) findViewById(R.id.btnGoogleMapsHTW);
        btnContact2 = (Button) findViewById(R.id.btnContact2);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnSetAlarm = (Button) findViewById(R.id.btnSetAlarm);
        btnSetTerm = (Button) findViewById(R.id.btnSetTerm);
        btnEmail = (Button) findViewById(R.id.btnEmail);
        btnSMS = (Button) findViewById(R.id.btnSMS);

        btnOpenGoogle.setOnClickListener(this);
        btnSearchInGoogle.setOnClickListener(this);
        btnGoogleMapsHTW.setOnClickListener(this);
        btnContact2.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnSetAlarm.setOnClickListener(this);
        btnSetTerm.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnSMS.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOpenGoogle:
                Uri googleURI = Uri.parse("http://www.google.com/");
                Intent googleIntent = new Intent(Intent.ACTION_VIEW, googleURI);
                startActivity(googleIntent);
                break;
            case R.id.btnSearchInGoogle:
                Intent intentGoogleSearch = new Intent("htw.berlin.s0539757.intent.action.searchGoogle");
                startActivityForResult(intentGoogleSearch, 1);
                break;
            case R.id.btnGoogleMapsHTW:
                Uri googleMapsUri = Uri.parse("https://www.google.de/maps/place/Hochschule+für+Technik+und+Wirtschaft+Berlin+-+Campus+Wilhelminenhof");
                Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
                startActivity(googleMapsIntent);
                break;
            case R.id.btnContact2:
                Intent contact2Intent = new Intent(Intent.ACTION_INSERT_OR_EDIT)
                break;
            case R.id.btnCall:

                break;
            case R.id.btnSetAlarm:

                break;
            case R.id.btnSetTerm:

                break;
            case R.id.btnEmail:

                break;
            case R.id.btnSMS:

                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String query = data.getStringExtra("SearchQuery");

        Uri uri = Uri.parse("http://www.google.com/#q=" + query);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
