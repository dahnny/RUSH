package com.danielogbuti.rush;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PositionActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private ImageView roadPicture;
    private TextView locationPlace;
    private TextView latlng;
    private static String TAG = "fish";
    private GoogleApiClient googleApiClient;
    private LocationRequest mLocationRequest;
    private Double presentLatitude;
    private Double presentLongitude;
    private AppCompatButton submitButton;
    String address;
    String city;
    String state;
    String country;
    String postalCode;
    String knownName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        roadPicture = (ImageView)findViewById(R.id.roadImage);
        locationPlace = (TextView)findViewById(R.id.locationPlace);
        latlng = (TextView)findViewById(R.id.latlng);
        submitButton = (AppCompatButton)findViewById(R.id.submitButton);

        Bitmap extras = (Bitmap)getIntent().getExtras().get("Tag");

        Log.i(TAG,extras + "");

        roadPicture.setImageBitmap(extras);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (submitButton.getText() != "Go to Payment"){
                    googleApiClient = new GoogleApiClient.Builder(PositionActivity.this)
                            .addConnectionCallbacks(PositionActivity.this)
                            .addOnConnectionFailedListener(PositionActivity.this)
                            .addApi(LocationServices.API)
                            .build();
                    googleApiClient.connect();
                }else{
                    Intent intent = new Intent(PositionActivity.this,PaymentChoice.class);
                    startActivity(intent);

                }




            }


        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000*60*1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null){
            Toast.makeText(this, "Cant get current location", Toast.LENGTH_SHORT).show();
        }else {

            presentLatitude = location.getLatitude();
            presentLongitude = location.getLongitude();

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());


            try {
                addresses = geocoder.getFromLocation(presentLatitude, presentLongitude, 1);
                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                 address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                 city = addresses.get(0).getLocality();
                 state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postalCode = addresses.get(0).getPostalCode();
                knownName = addresses.get(0).getFeatureName();
            }


            catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Getting location", Toast.LENGTH_SHORT).show();
            locationPlace.setText("Location: "+knownName);
            latlng.setText("Latitutde and Longitude: "+presentLatitude.toString()+presentLongitude.toString());
            submitButton.setText("Go to Payment");
            googleApiClient.disconnect();

        }



    }
}
