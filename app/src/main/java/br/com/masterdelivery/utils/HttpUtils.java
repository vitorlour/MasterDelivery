package br.com.masterdelivery.utils;


import android.os.StrictMode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public HttpUtils (){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public Object post(String myUrl, String jsonObject, boolean login) throws IOException {

        URL url = new URL(myUrl);

        // 1. Cria HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        // 2. adiciona o json no request body do POST
        setPostRequestContent(conn, jsonObject);

        // 3. faz a requisição post para a url fornecida
        conn.connect();

        // 4. retorno
        if(login){
            return conn.getHeaderField("Authorization");
        }else{
            return conn.getResponseCode();
        }
    }

    private void setPostRequestContent(HttpURLConnection conn,
                                       String jsonObject) throws IOException {

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject);
        writer.flush();
        writer.close();
        os.close();
    }
}
