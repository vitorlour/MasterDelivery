package br.com.masterdelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.masterdelivery.R;


public class CorridaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

        // hide the default actionbar
        getSupportActionBar().hide();

        // Recieve data

        String nomeEstabelecimento  = getIntent().getExtras().getString("Nome do Estabelecimento");
        String endEstabelecimento = getIntent().getExtras().getString("Endreço do estabelecimento");
        String nomeCliente = getIntent().getExtras().getString("Nome do cliente");
        String endCliente = getIntent().getExtras().getString("Endereço do cliente") ;
        String vlrEntrega = getIntent().getExtras().getString("Valor da entrega");
       // String plataforma = getIntent().getExtras().getString("Plataforma");
        String image_url = getIntent().getExtras().getString("logo_app_img") ;

        // ini views

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_nome_estabelecimento = findViewById(R.id.nome_estabelecimento_corrida);
        TextView tv_end_estabelecimento = findViewById(R.id.end_estabelecimento_corrida);
        TextView tv_nome_cliente = findViewById(R.id.nome_cliente_corrida) ;
        TextView tv_end_cliente = findViewById(R.id.end_cliente_corrida) ;
        TextView tv_vlr_entrega = findViewById(R.id.vlr_entrega_corrida);
       // TextView tv_plataforma  = findViewById(R.id.nm_plataforma_corrida) ;
        ImageView img = findViewById(R.id.aa_thumbnail);

        // setting values to each view

        tv_nome_estabelecimento.setText(nomeEstabelecimento);
        tv_end_estabelecimento.setText(endEstabelecimento);
        tv_nome_cliente.setText(nomeCliente);
        tv_end_cliente.setText(endCliente);
        tv_vlr_entrega.setText(vlrEntrega);

        //collapsingToolbarLayout.setTitle(nomeEstabelecimento);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);

        Button mAceitarCorrida = (Button) findViewById(R.id.btn_aceitar_corrida);


        mAceitarCorrida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CorridaAceitaActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
