package br.com.masterdelivery.listeners;


import br.com.masterdelivery.models.ErrorObject;

public interface RetrofitListener {
    void onResponseSuccess(String responseBodyString, int apiFlag);

    void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag);

}
