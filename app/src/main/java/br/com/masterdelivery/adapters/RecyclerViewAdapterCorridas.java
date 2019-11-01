package br.com.masterdelivery.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class RecyclerViewAdapterCorridas extends BaseAdapter {
    private Context ctx;
    private String[] lista;

    public RecyclerViewAdapterCorridas(Context ctx, String[] lista){
        this.ctx = ctx;
        this.lista = lista;
    }

    @Override
    public int getCount(){
        return lista.length;
    }

    @Override
    public Object getItem(int position){
        return lista[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TextView tv = new TextView(ctx);
        tv.setText(lista[position]);

        return tv;
    }
}
