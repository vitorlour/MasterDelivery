package br.com.masterdelivery.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import br.com.masterdelivery.R;
import br.com.masterdelivery.adapters.RecyclerViewAdapterCorridas;

public class MinhasEntregasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_entregas);

        String lista[] = new String[]{"Conta", "Valor", "Taxa",
                                      "iFood","100,00", "10,00",
                                      "iFood", "120,00", "12,00",
                                      "iFood", "50,00", "5,00",};

        GridView gv = (GridView) findViewById(R.id.grdEntregas);

        gv.setAdapter(new RecyclerViewAdapterCorridas(this,lista));


    }
}
