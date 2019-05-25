package br.com.masterdelivery.service;

import br.com.masterdelivery.models.Corrida;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CorridaService {

    @GET("corrida")
    Call<Corrida> buscarCorrida();
}
