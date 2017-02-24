package com.project.danilopereira.aula03.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.project.danilopereira.aula03.R;
import com.project.danilopereira.aula03.model.AndroidVersao;

import java.util.List;

/**
 * Created by danilopereira on 23/02/17.
 */

public class ListaAndroidAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private LayoutInflater layoutInflater;
    private List<AndroidVersao> versoes;

    public ListaAndroidAdapter(Context context, List<AndroidVersao> versoes){
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.versoes = versoes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new AndroidItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AndroidItemHolder androidItemHolder = (AndroidItemHolder) holder;

        androidItemHolder.tvNome.setText(versoes.get(position).getNome());
        androidItemHolder.tvApi.setText(versoes.get(position).getApi());
        androidItemHolder.tvVersao.setText(versoes.get(position).getVersao());

        Glide.with(context).load(versoes.get(position).getUrlImagem())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
        .into(androidItemHolder.ivIcone);


    }

    @Override
    public int getItemCount() {
        return versoes.size();
    }

    private class AndroidItemHolder extends RecyclerView.ViewHolder{

        ImageView ivIcone;
        TextView tvNome, tvApi, tvVersao;

        public AndroidItemHolder(View itemView) {
            super(itemView);

            ivIcone = (ImageView) itemView.findViewById(R.id.ivItem);
            tvNome = (TextView) itemView.findViewById(R.id.tvNome);
            tvApi = (TextView) itemView.findViewById(R.id.tvApi);
            tvVersao = (TextView) itemView.findViewById(R.id.tvVersion);
        }


    }
}
