package br.com.masterdelivery.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import br.com.masterdelivery.R;
import br.com.masterdelivery.listeners.RetrofitListener;
import br.com.masterdelivery.models.ErrorObject;
import br.com.masterdelivery.models.SairContaFakeAppsDTO;
import br.com.masterdelivery.models.UsuarioFakeAppsDTO;
import br.com.masterdelivery.retrofit.ApiServiceProvider;
import br.com.masterdelivery.utils.Constants;

public class RecyclerViewAdapterContas extends RecyclerView.Adapter<RecyclerViewAdapterContas.MyViewHolder> implements RetrofitListener {

    private Context mContext;
    private List<UsuarioFakeAppsDTO> mData;
    RequestOptions option;


    public RecyclerViewAdapterContas(Context mContext, List<UsuarioFakeAppsDTO> mData) {
        this.mContext = mContext;
        this.mData = mData;

        // Request option for Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }


    @Override
    public RecyclerViewAdapterContas.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.contas_row_item, parent, false);
        final RecyclerViewAdapterContas.MyViewHolder viewHolder = new RecyclerViewAdapterContas.MyViewHolder(view);

        viewHolder.mSairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the clicked item label
                String email = mData.get(viewHolder.getAdapterPosition()).getEmail();
                String plataforma = mData.get(viewHolder.getAdapterPosition()).getPlataforma();
                plataforma = plataformaConverter(plataforma);

                SairContaFakeAppsDTO dto = SairContaFakeAppsDTO.builder()
                                                               .email(email)
                                                               .plataforma(plataforma)
                                                               .build();

                ApiServiceProvider apiServiceProvider = ApiServiceProvider.getInstance(mContext);
                apiServiceProvider.deleteContaApp(RecyclerViewAdapterContas.this,dto);

                // Remove the item on remove/button click
                mData.remove(viewHolder.getAdapterPosition());

                notifyItemRemoved(viewHolder.getAdapterPosition());

                notifyItemRangeChanged(viewHolder.getAdapterPosition(),mData.size());

            }
        });

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerViewAdapterContas.MyViewHolder holder, int position) {

        holder.plataformaCadastrada.setText(mData.get(position).getEmail());
        holder.emailCadastro.setText(mData.get(position).getPlataforma());

        Glide.with(mContext).load(mData.get(position).getLogoPath()).apply(option).into(holder.img_thumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView plataformaCadastrada;
        TextView emailCadastro;
        Button mSairButton;


        ImageView img_thumbnail;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container_contas);
            emailCadastro = itemView.findViewById(R.id.email_cadastrado);
            plataformaCadastrada = itemView.findViewById(R.id.plataforma_cadastrada);
            mSairButton = (Button) itemView.findViewById(R.id.btn_sair_conta);

            img_thumbnail = itemView.findViewById(R.id.thumbnail);

        }
    }


    @Override
    public void onResponseSuccess(String responseBodyString, int apiFlag) {
    }

    @Override
    public void onResponseError(ErrorObject errorObject, Throwable throwable, int apiFlag) {
    }

    public String plataformaConverter(String plataforma){

        if(plataforma.equals(Constants.Plataforma.IFOOD)){
            plataforma = String.valueOf(Constants.PlataformaID.IFOOD_ID);
        }else if(plataforma.equals(Constants.Plataforma.RAPPI)){
            plataforma = String.valueOf(Constants.PlataformaID.RAPPI_ID);
        }
        return plataforma;

    }
}

