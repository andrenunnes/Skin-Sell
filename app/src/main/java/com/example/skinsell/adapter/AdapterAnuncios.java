package com.example.skinsell.adapter;
/*
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skinsell.R;
import com.example.skinsell.model.Anuncio;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAnuncios extends RecyclerView.Adapter<AdapterAnuncios.MyViewHolder>{

    private List<Anuncio> listaAnuncios;
    public AdapterAnuncios(List<Anuncio> lista) {
        this.listaAnuncios = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anuncios, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Anuncio anuncio = listaAnuncios.get(position);
        holder.titulo.setText(anuncio.getTitulo());
        holder.preco.setText(anuncio.getPreco());

    }

    @Override
    public int getItemCount() {
        return listaAnuncios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView foto;
        TextView titulo;
        TextView preco;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageViewAnuncio);
            titulo = itemView.findViewById(R.id.textViewTituloAnuncio);
            preco = itemView.findViewById(R.id.textViewPrecoAnuncio);
        }
    }

}*/


