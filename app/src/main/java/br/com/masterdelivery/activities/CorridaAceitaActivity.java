package br.com.masterdelivery.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import br.com.masterdelivery.R;
import br.com.masterdelivery.listeners.RetrofitListener;
import br.com.masterdelivery.models.Coordenadas;
import br.com.masterdelivery.models.Corrida;
import br.com.masterdelivery.models.ErrorObject;
import br.com.masterdelivery.retrofit.ApiServiceProvider;
import br.com.masterdelivery.transformer.CorridaTransformer;
import br.com.masterdelivery.utils.Constants;
import br.com.masterdelivery.utils.MasterDeliveryUtils;

public class CorridaAceitaActivity extends FragmentActivity implements OnMapReadyCallback ,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        RoutingListener,
        RetrofitListener {

    private static final String LOG_TAG = "CorridaAceitaActivity";

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LatLng lastKnownLatLng;
    private Corrida corrida;

    protected LatLng endEstabelecimento;
    protected LatLng endCliente;
    protected LatLng endAtual;

    private List<Polyline> polylines;

    private Button mButtonMap;

    private ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(CorridaAceitaActivity.this);

    private static final int[] COLORS = new int[]{R.color.primary_dark,R.color.primary,R.color.primary_light,R.color.accent,R.color.primary_dark_material_light};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida_aceita);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        polylines = new ArrayList<>();


        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)

                    .build();
        }

        corrida = (Corrida) getIntent().getSerializableExtra("CorridaClass");


        endEstabelecimento = MasterDeliveryUtils.getLocationFromAddress(CorridaAceitaActivity.this, corrida.getEndEstabelecimento());
        endCliente = MasterDeliveryUtils.getLocationFromAddress(CorridaAceitaActivity.this, corrida.getEndCliente());

        if(corrida.getStatusCorrida() == 0){
            endAtual = endEstabelecimento;
        }else if (corrida.getStatusCorrida() == 1){
            mButtonMap.setText("ENTREGA EFETUADA");
            endAtual = endCliente;
        }


        route();

        mButtonMap = (Button) findViewById(R.id.btn_mapa);

        mButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                apiServiceProvider.getCorridaAndamento(CorridaAceitaActivity.this);

            }
        });


    }

    public void route(){
        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(true)
                .waypoints(getLocation(), endAtual)
                .key("AIzaSyBOZWbNExpYgpcY4ohw6wwg7EP4PcSAUPo")
                .build();
        routing.execute();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.moveCamera(CameraUpdateFactory.newLatLng(getLocation()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(getLocation(), 15));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onPause();
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        lastKnownLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lastKnownLatLng));
        route();
    }

    protected void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }


    public LatLng getLocation() {
        LatLng latLng = null;

        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }

            location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null){
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }

        }
        return latLng;
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Log.i(LOG_TAG, "RouteException ", e);
    }

    @Override
    public void onRoutingStart() {
        Log.i(LOG_TAG, "start routing");

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(getLocation());
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.moveCamera(zoom);


        if(polylines.size()>0) {
            for (Polyline poly : polylines) {
                poly.remove();
            }
        }

        polylines = new ArrayList<>();


        //add route(s) to the map.
    //    for (int i = 0; i <route.size(); i++) {

            //In case of more than 5 alternative routes
            int colorIndex = shortestRouteIndex % COLORS.length;

            PolylineOptions polyOptions = new PolylineOptions();
            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
            polyOptions.width(10 + shortestRouteIndex * 3);
            polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
            Polyline polyline = mMap.addPolyline(polyOptions);
            polylines.add(polyline);

      //  }



        MarkerOptions marker = new MarkerOptions();
        marker.position(endAtual);
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        mMap.addMarker(marker);

    }

    @Override
    public void onRoutingCancelled() {
    }

    @Override
    public void onResponseSuccess(String responseBodyString, int apiFlag) {
        switch (apiFlag) {
            case Constants.ApiFlags.POST_PEDIDO_COLETADO:
                endAtual = endCliente;
                route();
                mButtonMap.setText("ENTREGA EFETUADA");
                break;
            case Constants.ApiFlags.GET_CORRIDA_EM_ANDAMENTO:
                if(!responseBodyString.isEmpty()){
                    corrida = new Gson().fromJson(responseBodyString, Corrida.class);
                    corrida = CorridaTransformer.transform(corrida);

                    LatLng latLng = getLocation();
                    Coordenadas coordenadas = new Coordenadas(latLng.latitude, latLng.longitude);

                    if(corrida.getStatusCorrida() == 0){
                        endAtual = endEstabelecimento;
                        apiServiceProvider.postPedidoColetado(CorridaAceitaActivity.this, coordenadas);
                    }else if (corrida.getStatusCorrida() == 1){

                        endAtual = endCliente;
                        apiServiceProvider.postEntregaPedidoEfetuada(CorridaAceitaActivity.this, coordenadas);
                    }

                }
                break;
            case Constants.ApiFlags.POST_CORRIDA_ENTREGA_PEDIDO_EFETUADA:
                Toast.makeText(CorridaAceitaActivity.this,
                        "Pedido entregue com sucesso !",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        switch (apiFlag) {
            case Constants.ApiFlags.POST_PEDIDO_COLETADO:
                Toast.makeText(CorridaAceitaActivity.this,
                        errorObject.getMessage(),
                        Toast.LENGTH_SHORT).show();

                break;
            case Constants.ApiFlags.POST_CORRIDA_ENTREGA_PEDIDO_EFETUADA:
                Toast.makeText(CorridaAceitaActivity.this,
                        errorObject.getMessage(),
                        Toast.LENGTH_SHORT).show();
                route();
                break;
        }
    }
}
