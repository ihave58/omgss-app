package in.omgss.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import in.omgss.constants.PreferenceConstants;
import in.omgss.data.preferences.PreferenceManager;

public class LocationUpdateService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationListener locationListener;

    private class LocationListener implements com.google.android.gms.location.LocationListener {

        public LocationListener() {

        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                String currentLat = "" + location.getLatitude();
                String currentLng = "" + location.getLongitude();
                PreferenceManager.getInstance().putString(PreferenceConstants.LATITUDE, currentLat);
                PreferenceManager.getInstance().putString(PreferenceConstants.LONGITUDE, currentLng);
            }
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        boolean stopService = false;
        if (intent != null)
            stopService = intent.getBooleanExtra("stopservice", false);

        locationListener = new LocationListener();
        if (stopService)
            stopLocationUpdates();
        else {
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            } else {
                mGoogleApiClient.disconnect();
                mGoogleApiClient.connect();
            }
        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, locationListener);

        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {

    }

    @Override
    public void onConnected(Bundle arg0) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        mLocationRequest.setSmallestDisplacement(5.0f);
        mLocationRequest.setFastestInterval(1000);
        startLocationUpates();
    }

    private void startLocationUpates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListener);
    }


    @Override
    public void onConnectionSuspended(int arg0) {

    }

}