package br.com.masterdelivery.retrofit;


import br.com.masterdelivery.models.Coordenadas;
import br.com.masterdelivery.models.SairContaFakeAppsDTO;
import br.com.masterdelivery.models.UsuarioFakeAppsDTO;
import br.com.masterdelivery.utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiServices {

    //@GET(Constants.UrlPath.GET_CORRIDA)
    Call<ResponseBody> getSomething(@Path("service") String service);

    @POST(Constants.UrlPath.POST_CORRIDA)
    Call<ResponseBody> postCorridas(@Body Coordenadas coordenadas);

    @POST(Constants.UrlPath.POST_VINCULAR_APPS)
    Call<ResponseBody> postVincularApps(@Body UsuarioFakeAppsDTO dto);

    @GET(Constants.UrlPath.GET_CONTAS_APPS)
    Call<ResponseBody> getContasApps();

    @POST(Constants.UrlPath.DELETE_CONTA_APP)
    Call<ResponseBody> deleteContaApp(@Body SairContaFakeAppsDTO dto);

}
