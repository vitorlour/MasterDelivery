package br.com.masterdelivery.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.masterdelivery.R;
import br.com.masterdelivery.adapters.RecyclerViewAdapterContas;
import br.com.masterdelivery.listeners.RetrofitListener;
import br.com.masterdelivery.models.ErrorObject;
import br.com.masterdelivery.models.UsuarioFakeAppsDTO;
import br.com.masterdelivery.retrofit.ApiServiceProvider;
import br.com.masterdelivery.transformer.UsuarioFakeAppsDTOTransformer;
import br.com.masterdelivery.utils.Constants;
import br.com.masterdelivery.utils.MasterDeliveryUtils;

public class ContasActivity extends AppCompatActivity implements RetrofitListener {

    private AlertDialog dialog = null;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerview_id_contas);

        ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
        apiServiceProvider.getContasApps(ContasActivity.this);


        FloatingActionButton fab = findViewById(R.id.add_contas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ContasActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login_apps, null);
                final EditText mEmail = (EditText) mView.findViewById(R.id.etEmail);
                final EditText mSenha = (EditText) mView.findViewById(R.id.etPassword);
                final EditText mPlataforma = (EditText) mView.findViewById(R.id.etPlataforma);

                Button mLogin = (Button) mView.findViewById(R.id.btnLogin);

                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        UsuarioFakeAppsDTO usuario = UsuarioFakeAppsDTO.builder()
                                                                       .email(String.valueOf(mEmail.getText()))
                                                                       .senha(String.valueOf(mSenha.getText()))
                                                                       .plataforma(String.valueOf(mPlataforma.getText()))
                                                                       .build();
                        if (!usuario.getEmail().isEmpty() && !usuario.getSenha().isEmpty() && !usuario.getPlataforma().isEmpty()) {
                            ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(ContasActivity.this);
                            apiServiceProvider.postVincularApps(ContasActivity.this, usuario);

                        } else {
                            Toast.makeText(ContasActivity.this,
                                    R.string.erro_login_msg,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

        });
    }

    
    @Override
    public void onResponseSuccess(String responseBodyString, int apiFlag) {

        switch (apiFlag) {
            case Constants.ApiFlags.POST_VINCULAR_APPS:
                Toast.makeText(ContasActivity.this,
                        R.string.sucesso_login_msg,
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(this);
                apiServiceProvider.getContasApps(ContasActivity.this);
                break;
            case Constants.ApiFlags.GET_CONTAS_APPS:
                if(!responseBodyString.equals("[]")){
                    List<UsuarioFakeAppsDTO> usuario = Arrays.asList(MasterDeliveryUtils.usuarioFakeAppsFromJson(responseBodyString));
                    usuario = UsuarioFakeAppsDTOTransformer.transform(usuario);
                    setUpRecyclerView(usuario);
                }
                break;

        }

    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
        switch (apiFlag) {
            case Constants.ApiFlags.POST_CORRIDAS:
                Toast.makeText(ContasActivity.this,
                        R.string.erro_login_post_msg,
                        Toast.LENGTH_SHORT).show();
                break;
        }

    }


    private void setUpRecyclerView(List<UsuarioFakeAppsDTO> lstUsuario) {

        RecyclerViewAdapterContas myadapter = new RecyclerViewAdapterContas(ContasActivity.this, lstUsuario);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContasActivity.this));
        recyclerView.setAdapter(myadapter);
    }


}

