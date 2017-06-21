package com.example.harsh.a5;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;
/**
 * Created by harsh on 4/2/17.
 */

public class Locator
{
    private MainActivity mainActivity;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Locator(MainActivity ma)
    {
        mainActivity = ma;

        if (checkPermission())
        {
            setUpLocationManager();
            determineLocation();
            //if (loc != null)
            //    mainActivity.setData(loc.getLatitude(), loc.getLongitude());
        }
    }

    void setUpLocationManager()
    {
        if(locationManager!=null) {
            return;
        }

        if(!checkPermission())
        {
            return;
        }

        // Get the system's Location Manager
        locationManager = (LocationManager) mainActivity.getSystemService(LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                // Called when a new location is found by the network location provider.
                Toast.makeText(mainActivity, "Update from " + location.getProvider(), Toast.LENGTH_SHORT).show();
                mainActivity.setData(location.getLatitude(), location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Nothing to do here
            }

            @Override
            public void onProviderEnabled(String provider) {
                    // Nothing to do here
            }

            @Override
            public void onProviderDisabled(String provider) {
                // Nothing to do here
            }
        };
        // Register the listener with the Location Manager to receive GPS location updates
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
    }

    public void shutdown()
    {
        locationManager.removeUpdates(locationListener);
        locationManager = null;
    }

    void determineLocation()
    {
        if (!checkPermission())
            return;

        if (locationManager == null)
            setUpLocationManager();

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null)
            {
                mainActivity.setData(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(mainActivity, "Using " + LocationManager.NETWORK_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
            if(loc == null)
            {
                Log.d(TAG, "No Location determine!!");
            }
        }

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (loc != null) {
                mainActivity.setData(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(mainActivity, "Using " + LocationManager.PASSIVE_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "1: No Location id not determine!!");
                return;
            }
        }

        if (locationManager != null) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                mainActivity.setData(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(mainActivity, "Using " + LocationManager.GPS_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "2: No Location id not determine!!");
                return;
            }
            // If you get here, you got no location at all
            mainActivity.noLocationAvailable();
            return;
        }
    }



    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            return false;
        }
        return true;
    }
}
