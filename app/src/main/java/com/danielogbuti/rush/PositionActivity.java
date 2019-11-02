package com.danielogbuti.rush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class PositionActivity extends AppCompatActivity {

    private ImageView roadPicture;
    private TextView locationPlace;
    private TextView latlng;
    private static String TAG = "fish";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        roadPicture = (ImageView)findViewById(R.id.roadImage);
        locationPlace = (TextView)findViewById(R.id.locationPlace);
        latlng = (TextView)findViewById(R.id.latlng);

        Bitmap extras = (Bitmap)getIntent().getExtras().get("Tag");

        Log.i(TAG,extras + "");

        roadPicture.setImageBitmap(extras);
    }


}
