package br.com.masterdelivery.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import br.com.masterdelivery.R;
import br.com.masterdelivery.activities.CorridaActivity;
import br.com.masterdelivery.models.Corrida;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Corrida> mData;
    RequestOptions option;


    public RecyclerViewAdapter(Context mContext, List<Corrida> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.corrida_row_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, CorridaActivity.class);
                i.putExtra("CorridaClass", mData.get(viewHolder.getAdapterPosition()));
                mContext.startActivity(i);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.idPedido.setText(mData.get(position).getId());
        holder.nomeEstabelecimento.setText(mData.get(position).getNomeEstabelecimento());
        holder.enderecoEstabelecimento.setText(mData.get(position).getEndEstabelecimento());
        holder.vlr_entrega.setText(mData.get(position).getValorEntrega());

        Glide.with(mContext).load(mData.get(position).getLogoPath()).apply(option).into(holder.img_thumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idPedido;
        TextView nomeEstabelecimento;
        TextView enderecoEstabelecimento;
        TextView vlr_entrega;
        ImageView img_thumbnail;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            idPedido = itemView.findViewById(R.id.id_pedido_corrida_row);


            nomeEstabelecimento = itemView.findViewById(R.id.nome_estabelecimento);
            enderecoEstabelecimento = itemView.findViewById(R.id.end_estabelecimento);
            vlr_entrega = itemView.findViewById(R.id.vlr_entrega);
            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }


}
