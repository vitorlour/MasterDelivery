package br.com.masterdelivery.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import br.com.masterdelivery.models.Coordenadas;
import br.com.masterdelivery.models.Corrida;
import br.com.masterdelivery.models.UsuarioFakeAppsDTO;

public class MasterDeliveryUtils {

    public static Corrida[] corridaFromJson(String json){
       return new Gson().fromJson(json, Corrida[].class);
    }

    public static UsuarioFakeAppsDTO[] usuarioFakeAppsFromJson(String json){
        return new Gson().fromJson(json, UsuarioFakeAppsDTO[].class);
    }

    public static LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

        }

        return p1;
    }


    public static Coordenadas getLocation(Context context, Activity activity) {

        Activity act = new Activity();
        Coordenadas coordenadas = null;

        LocationManager locManager = (LocationManager) act.getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null){
                coordenadas = new Coordenadas();
                coordenadas.setLongitude(location.getLongitude());
                coordenadas.setLatitude(location.getLatitude());
            }

        }
        return coordenadas;
    }
}
