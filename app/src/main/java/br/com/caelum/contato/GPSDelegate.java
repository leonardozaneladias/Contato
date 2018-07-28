package br.com.caelum.contato;

import android.content.Context;
import android.location.Location;

public interface GPSDelegate {

    Context getContext();

    void lidaCom(Location lastLocation);

}
