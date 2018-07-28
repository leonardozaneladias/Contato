package br.com.caelum.contato;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapaFragment extends SupportMapFragment implements GPSDelegate {

    private GoogleMap map;
    private GPS gps;

    @Override
    public void onResume() {
        super.onResume();

        gps = new GPS(this);
        gps.fazBusca();
        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                map = googleMap;


                AlunoDAO alunoDAO = new AlunoDAO(getContext());
                List<Aluno> alunos = alunoDAO.getLista();

                final Geocoder geocoder = new Geocoder(getContext());

                try {

                    for (Aluno aluno : alunos){

                        MarkerOptions marker = new MarkerOptions();


                            List<Address> endereco = geocoder.getFromLocationName(aluno.getEndereco(), 1);
                            Address address = endereco.get(0);
                            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());



                            marker.position(latLng).title(aluno.getNome()).snippet(aluno.getEndereco());

                            googleMap.addMarker(marker);



                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    List<Address> enderecoCaelum = geocoder.getFromLocationName("Rua Vergueiro 3185 Vila Mariana", 1);
                    Address addressCaelum = enderecoCaelum.get(0);
                    LatLng latLng = new LatLng(addressCaelum.getLatitude(), addressCaelum.getLongitude());

                } catch (IOException e) {
                    e.printStackTrace();
                }

                map.setMyLocationEnabled(true);
                //googleMap.setTrafficEnabled(true);




            }
        });
    }

    @Override
    public void lidaCom(Location lastLocation) {
        cetralizaNo(new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()));
    }

    private void cetralizaNo(LatLng local) {
        MarkerOptions marker = new MarkerOptions();
        marker.position(local).title("Eu").snippet("Minha localizacao atual");
        map.addMarker(marker);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(local,15));
    }

    @Override
    public void onPause() {
        super.onPause();
        gps.cancelaBusca();

    }
}
