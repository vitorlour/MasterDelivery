package br.com.masterdelivery.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import br.com.masterdelivery.R;
import br.com.masterdelivery.adapters.RecyclerViewAdapter;
import br.com.masterdelivery.listeners.RetrofitListener;
import br.com.masterdelivery.models.Coordenadas;
import br.com.masterdelivery.models.Corrida;
import br.com.masterdelivery.models.ErrorObject;
import br.com.masterdelivery.retrofit.ApiServiceProvider;
import br.com.masterdelivery.transformer.CorridaTransformer;
import br.com.masterdelivery.utils.Constants;
import br.com.masterdelivery.utils.MasterDeliveryUtils;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RetrofitListener {


    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recyclerviewid);

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);

        apiServiceProvider.getCorridaAndamento(this);

        apiServiceProvider.postCorridas(this, getLocation());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_contas) {
            Intent intent = new Intent(getApplicationContext(), ContasActivity.class);
            startActivity(intent);
            onPause();
        } else if (id == R.id.nav_minhas_entregas) {
            Intent intent = new Intent(getApplicationContext(), MinhasEntregasActivity.class);
            startActivity(intent);
            onPause();
        }else if (id == R.id.nav_rentabilidade_apps) {
            Intent intent = new Intent(getApplicationContext(), RentabilidadeActivity.class);
            startActivity(intent);
            onPause();
        }else if (id == R.id.nav_alterar_senha) {

        } else if (id == R.id.nav_sair) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResponseSuccess(String responseBodyString, int apiFlag) {
        switch (apiFlag) {
            case Constants.ApiFlags.POST_CORRIDAS:
                if(!responseBodyString.isEmpty()){
                    List<Corrida> corrida = Arrays.asList(MasterDeliveryUtils.corridaFromJson(responseBodyString));
                    corrida = CorridaTransformer.transform(corrida);
                    setUpRecyclerView(corrida);
                }
                break;
            case Constants.ApiFlags.GET_CORRIDA_EM_ANDAMENTO:
                if(!responseBodyString.isEmpty()){
                    Corrida corridaa = new Gson().fromJson(responseBodyString, Corrida.class);
                    corridaa = CorridaTransformer.transform(corridaa);

                    if(!corridaa.getStatusCorrida().equals(0)){
                        Intent i = new Intent(getApplicationContext(), CorridaAceitaActivity.class);
                        i.putExtra("CorridaClass", corridaa);
                        startActivity(i);
                        onPause();
                    }
                }

                break;
        }

    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        switch (apiFlag) {
            case Constants.ApiFlags.POST_CORRIDAS:
                Toast.makeText(MenuActivity.this,
                        errorObject.getMessage(),
                        Toast.LENGTH_SHORT).show();
                break;
            case Constants.ApiFlags.GET_CORRIDA_EM_ANDAMENTO:
               if(errorObject.getStatus() == 403){
                   Toast.makeText(MenuActivity.this,
                           errorObject.getMessage(),
                           Toast.LENGTH_SHORT).show();
               }

                break;
        }

    }


    public Coordenadas getLocation() {
        Coordenadas coordenadas = null;

         LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
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

    private void setUpRecyclerView(List<Corrida> lstCorrida) {

        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstCorrida);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }
}
