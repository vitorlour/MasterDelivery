package br.com.masterdelivery.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import br.com.masterdelivery.R;
import br.com.masterdelivery.utils.HttpUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private static final String TAG = "RecuperarSenhaActivity";

    private static final String URI_RECUPERAR_SENHA = "http://10.0.3.2:8080/usuario/recuperarsenha";
    public static final String POR_FAVOR_TENTE_NOVAMENTE = "Por favor tente novamente";


    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.btn_forget_pass)
    Button _forgetPassButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);
        ButterKnife.bind(this);

        _forgetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetPass();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void forgetPass() {
        Log.d(TAG, "Esqueci a senha");

        if (!validate()) {
            onForgetPassFailed(POR_FAVOR_TENTE_NOVAMENTE);
            return;
        }

        _forgetPassButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RecuperarSenhaActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Enviando E-mail...");
        progressDialog.show();


        String email = _emailText.getText().toString();

        HttpUtils http = new HttpUtils();
        Gson gson = new Gson();

        Object responseCode = null;

        try {
            responseCode = http.post(URI_RECUPERAR_SENHA, gson.toJson(email), false);
        } catch (IOException e) {
            Log.d(TAG, "Erro ao efetuar login");
            return;
        }

        if (!responseCode.equals(200)) {
            if (responseCode.equals(404)) {
                onForgetPassFailed("E-mail não encontrado !");
            }
            onForgetPassFailed(POR_FAVOR_TENTE_NOVAMENTE);
            progressDialog.dismiss();
            return;
        }


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onForgetPassSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onForgetPassSuccess() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onForgetPassFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        _forgetPassButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("por favor, entre com um e-mail válido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        return valid;
    }

}
