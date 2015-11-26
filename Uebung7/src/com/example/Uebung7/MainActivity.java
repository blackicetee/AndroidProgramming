package com.example.Uebung7;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;

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
    Button btnGallery;
    Button btnPhoto;
    Button btnVideo;
    Button btnVibrate;

    public void sendSMS(String number) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }

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
        btnGallery = (Button) findViewById(R.id.btnGallery);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);
        btnVideo = (Button) findViewById(R.id.btnVideo);
        btnVibrate = (Button) findViewById(R.id.btnVibrate);

        btnOpenGoogle.setOnClickListener(this);
        btnSearchInGoogle.setOnClickListener(this);
        btnGoogleMapsHTW.setOnClickListener(this);
        btnContact2.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnSetAlarm.setOnClickListener(this);
        btnSetTerm.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnSMS.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        btnVibrate.setOnClickListener(this);
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
                Intent intentGoogleSearch = new Intent(this, GoogleSearchActivity.class);
                startActivityForResult(intentGoogleSearch, 1);
                break;
            case R.id.btnGoogleMapsHTW:
                Uri googleMapsUri = Uri.parse("https://www.google.de/maps/place/Hochschule+für+Technik+und+Wirtschaft+Berlin+-+Campus+Wilhelminenhof/");
                Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
                if (googleMapsIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(googleMapsIntent);
                }
                break;
            case R.id.btnContact2:
                Intent contact2Intent = new Intent(this, EditContact2.class);
                startActivity(contact2Intent);
                break;
            case R.id.btnCall:
                Intent callIntent = new Intent(this, Call.class);
                startActivity(callIntent);
                break;
            case R.id.btnSetAlarm:
                Intent alarmIntent = new Intent(this, Alarm.class);
                startActivity(alarmIntent);
                break;
            case R.id.btnSetTerm:
                Intent termIntent = new Intent(this, TimeTable.class);
                startActivity(termIntent);
                break;
            case R.id.btnEmail:
                Intent emailIntent = new Intent(this, Email.class);
                startActivity(emailIntent);
                break;
            case R.id.btnSMS:
                sendSMS("02");
                break;
            case R.id.btnGallery:
                Intent galleryIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(galleryIntent);
                }
                break;
            case R.id.btnPhoto:
                Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (photoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(photoIntent);
                }
                break;
            case R.id.btnVideo:
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (videoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(videoIntent);
                }
                break;
            case R.id.btnVibrate:
                try {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(100);
                } catch (Exception e) {
                    Toast.makeText(this, "Your device does not support vibrate", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        String query = data.getStringExtra("SearchQuery");

        Uri uri = Uri.parse("http://www.google.com/#q=" + query);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
