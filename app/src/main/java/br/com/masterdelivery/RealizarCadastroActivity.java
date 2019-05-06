package br.com.masterdelivery;

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

import br.com.masterdelivery.dto.Usuario;
import br.com.masterdelivery.utils.HttpUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RealizarCadastroActivity extends AppCompatActivity {
    private static final String TAG = "RealizarCadastroActivity";

    private static final String URI_REALIZAR_CADASTRO = "http://10.0.3.2:8080/usuario/realizarcadastro";

    public static final String TENTE_NOVAMENTE = "Por favor Tente novamente";

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_cadastro);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Cadastro de Usuário");

        if (!validate()) {
            onSignupFailed(TENTE_NOVAMENTE);
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RealizarCadastroActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando a conta...");
        progressDialog.show();

        Usuario usuario = Usuario.builder()
                .email(_emailText.getText().toString())
                .senha(_passwordText.getText().toString())
                .build();

        HttpUtils http = new HttpUtils();
        Gson gson = new Gson();

        Object responseCode = null;

        try {
            responseCode = http.post(URI_REALIZAR_CADASTRO, gson.toJson(usuario), false);
        } catch (IOException e) {
            Log.d(TAG,"Erro ao efetuar login");
            return;
        }

        if(!responseCode.equals(200)){
            if(responseCode.equals(302)){
                onSignupFailed("E-mail já cadastrado");
            }else{
                onSignupFailed(TENTE_NOVAMENTE);
            }
            progressDialog.dismiss();
            return;
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed(String msg) {
        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Por favor, entre com um e-mail válido");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Senha entre 4 a 10 caracteres !");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("A Senha não é igual !");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
}