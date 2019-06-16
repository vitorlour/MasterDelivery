package br.com.masterdelivery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.masterdelivery.R;
import br.com.masterdelivery.listeners.RetrofitListener;
import br.com.masterdelivery.models.AceitarCorridaDTO;
import br.com.masterdelivery.models.Corrida;
import br.com.masterdelivery.models.ErrorObject;
import br.com.masterdelivery.retrofit.ApiServiceProvider;
import br.com.masterdelivery.utils.Constants;


public class CorridaActivity extends AppCompatActivity implements RetrofitListener {

    private Corrida corrida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

        // hide the default actionbar
        getSupportActionBar().hide();

        // Recieve data
        corrida = (Corrida) getIntent().getSerializableExtra("CorridaClass");

        TextView tv_id_pedido = findViewById(R.id.id_pedido_corrida);

        TextView tv_nome_estabelecimento = findViewById(R.id.nome_estabelecimento_corrida);
        TextView tv_end_estabelecimento = findViewById(R.id.end_estabelecimento_corrida);
        TextView tv_nome_cliente = findViewById(R.id.nome_cliente_corrida) ;
        TextView tv_end_cliente = findViewById(R.id.end_cliente_corrida) ;
        TextView tv_vlr_entrega = findViewById(R.id.vlr_entrega_corrida);
        ImageView img = findViewById(R.id.aa_thumbnail);

        // setting values to each view

        tv_id_pedido.setText(corrida.getId());

        tv_nome_estabelecimento.setText(corrida.getNomeEstabelecimento());
        tv_end_estabelecimento.setText(corrida.getEndEstabelecimento());
        tv_nome_cliente.setText(corrida.getNomeCliente());
        tv_end_cliente.setText(corrida.getEndCliente());
        tv_vlr_entrega.setText(corrida.getValorEntrega());


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        // set image using Glide
        Glide.with(this).load(corrida.getLogoPath()).apply(requestOptions).into(img);

        Button mAceitarCorrida = (Button) findViewById(R.id.btn_aceitar_corrida);


        mAceitarCorrida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AceitarCorridaDTO dto = AceitarCorridaDTO.builder()
                                                         .tokenCorrida(corrida.getTokenCorrida())
                                                         .build();

                ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(CorridaActivity.this);
                apiServiceProvider.postAceitarCorrida(CorridaActivity.this, dto);
            }
        });
    }



    @Override
    public void onResponseSuccess(String responseBodyString, int apiFlag) {

        switch (apiFlag) {
            case Constants.ApiFlags.POST_ACEITAR_CORRIDA:
                Intent intent = new Intent(getApplicationContext(), CorridaAceitaActivity.class);
                intent.putExtra("CorridaClass", corrida);
                startActivity(intent);
                finish();
                break;
        }

    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        switch (apiFlag) {
            case Constants.ApiFlags.POST_ACEITAR_CORRIDA:
                Toast.makeText(CorridaActivity.this,
                        errorObject.getMessage(),
                        Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }
}
