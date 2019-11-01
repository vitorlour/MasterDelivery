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

        String lista[] = new String[]{"Conta", "Estabelecimento", "Valor",
                                      "iFood", "McDonalds",       "35,00",
                                      "iFood", "Pizza Hut",       "28,00",
                                      "Rappi", "Pão de Açúcar",   "15,00",};


        GridView gv = (GridView) findViewById(R.id.grdEntregas);

        gv.setAdapter(new RecyclerViewAdapterCorridas(this,lista));


    }
}
