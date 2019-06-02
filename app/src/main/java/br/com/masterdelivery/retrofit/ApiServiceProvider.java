package br.com.masterdelivery.retrofit;

import android.content.Context;

import br.com.masterdelivery.listeners.RetrofitListener;
import br.com.masterdelivery.models.AceitarCorridaDTO;
import br.com.masterdelivery.models.Coordenadas;
import br.com.masterdelivery.models.SairContaFakeAppsDTO;
import br.com.masterdelivery.models.UsuarioFakeAppsDTO;
import br.com.masterdelivery.utils.Constants;
import br.com.masterdelivery.utils.HttpUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiServiceProvider extends RetrofitBase {
    private static ApiServiceProvider apiServiceProvider;
    private ApiServices apiServices;

    private ApiServiceProvider(Context context) {
        super(context, true);
        apiServices = retrofit.create(ApiServices.class);
    }

    public static ApiServiceProvider getInstance(Context context) {
        if (apiServiceProvider == null) {
            apiServiceProvider = new ApiServiceProvider(context);
        }
        return apiServiceProvider;
    }

    public void getSomething(String parameter, final RetrofitListener retrofitListener) {
        Call<ResponseBody> call = apiServices.getSomething(parameter);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                validateResponse(response, retrofitListener, Constants.ApiFlags.GET_SOMETHING);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_SOMETHING);
            }
        });
    }

    public void getContasApps(final RetrofitListener retrofitListener) {
        Call<ResponseBody> call = apiServices.getContasApps();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                validateResponse(response, retrofitListener, Constants.ApiFlags.GET_CONTAS_APPS);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //pass correct flag to differentiate between multiple api calls in same activity/frag
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.GET_CONTAS_APPS);
            }
        });
    }

    public void postCorridas(final RetrofitListener retrofitListener, Coordenadas coordenadas) {
        Call<ResponseBody> call = apiServices.postCorridas(coordenadas);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                validateResponse(response, retrofitListener, Constants.ApiFlags.POST_CORRIDAS);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //pass correct flag to differentiate between multiple api calls in same activity/frag
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.POST_CORRIDAS);
            }
        });
    }

    public void postVincularApps(final RetrofitListener retrofitListener, UsuarioFakeAppsDTO dto) {
        Call<ResponseBody> call = apiServices.postVincularApps(dto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                validateResponse(response, retrofitListener, Constants.ApiFlags.POST_VINCULAR_APPS);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //pass correct flag to differentiate between multiple api calls in same activity/frag
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.POST_VINCULAR_APPS);
            }
        });
    }


    public void deleteContaApp(final RetrofitListener retrofitListener, SairContaFakeAppsDTO dto) {
        Call<ResponseBody> call = apiServices.deleteContaApp(dto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                validateResponse(response, retrofitListener, Constants.ApiFlags.DELETE_CONTA_APP);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //pass correct flag to differentiate between multiple api calls in same activity/frag
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.DELETE_CONTA_APP);
            }
        });
    }

    public void postAceitarCorrida(final RetrofitListener retrofitListener, AceitarCorridaDTO dto) {
        Call<ResponseBody> call = apiServices.postAceitarCorrida(dto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                validateResponse(response, retrofitListener, Constants.ApiFlags.POST_ACEITAR_CORRIDA);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //pass correct flag to differentiate between multiple api calls in same activity/frag
                retrofitListener.onResponseError(HttpUtil.getServerErrorPojo(context), t, Constants.ApiFlags.POST_ACEITAR_CORRIDA);
            }
        });
    }

}
