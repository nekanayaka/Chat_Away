package net.ddns.nimna.chat_away_v2;

import android.location.LocationListener;
import android.location.*;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by RYAN on 2/12/2016.
 */
public class MyLocationListener implements LocationListener {
    @Override

    public void onLocationChanged(Location loc)

    {

        loc.getLatitude();

        loc.getLongitude();


    }

    public double getLatitude(Location loc) {
        return loc.getLatitude();
    }

    public double getLongitude(Location loc) {
        return loc.getLongitude();
    }


    @Override

    public void onProviderDisabled(String provider)

    {


    }


    @Override

    public void onProviderEnabled(String provider)

    {

    }


    @Override

    public void onStatusChanged(String provider, int status, Bundle extras)

    {


    }

}

