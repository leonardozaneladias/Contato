package br.com.caelum.contato;


import android.annotation.SuppressLint;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

class GPS extends LocationCallback {

    private FusedLocationProviderClient client;
    private GPSDelegate delegate;

    public GPS(GPSDelegate delegate) {
        this.delegate = delegate;
        this.client = LocationServices.getFusedLocationProviderClient(delegate.getContext());
    }

    @SuppressLint("MissingPermission")
    public void fazBusca() {
        LocationRequest req = new LocationRequest();

        req.setInterval(1000);
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        req.setSmallestDisplacement(10);

        client.requestLocationUpdates(req, this, null);
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);

        delegate.lidaCom(locationResult.getLastLocation());

    }

    public void cancelaBusca() {
        client.removeLocationUpdates(this);
    }

}
