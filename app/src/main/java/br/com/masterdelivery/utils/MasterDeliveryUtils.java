package br.com.masterdelivery.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.gson.Gson;

import br.com.masterdelivery.models.Corrida;
import br.com.masterdelivery.models.UsuarioFakeAppsDTO;

public class MasterDeliveryUtils {

    public static Corrida[] corridaFromJson(String json){
       return new Gson().fromJson(json, Corrida[].class);
    }

    public static UsuarioFakeAppsDTO[] usuarioFakeAppsFromJson(String json){
        return new Gson().fromJson(json, UsuarioFakeAppsDTO[].class);
    }



    public static Drawable getImage(Context context, String name) {
        return context.getResources().getDrawable(context.getResources().getIdentifier(name, "drawable", context.getPackageName()));
    }
}
