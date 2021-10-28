package com.example.skinsell.adapter;

import android.content.Context;
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
import com.squareup.picasso.Target;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AdapterAnuncio extends RecyclerView.Adapter<AdapterAnuncio.MyViewHolder> {

    private List<Anuncio> anuncios;
    private Context context;

    public AdapterAnuncio(List<Anuncio> anuncios, Context context) {
        this.anuncios = anuncios;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_anuncios, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NumberFormat precoFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

        Anuncio anuncio = anuncios.get(position);
        holder.titulo.setText(anuncio.getTitulo());
        holder.preco.setText(precoFormat.format(Double.parseDouble(anuncio.getPreco())));


        //Primeira imagem
        List<String> urlFotos = anuncio.getFotos();
        String urlCapa = urlFotos.get(0);

        Picasso.get().load(urlCapa).into(holder.foto);


    }

    @Override
    public int getItemCount() {
        return anuncios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        TextView preco;
        ImageView foto;

        public MyViewHolder(View itemView){
            super(itemView);

            titulo = itemView.findViewById(R.id.textViewTituloAnuncio);
            preco = itemView.findViewById(R.id.textViewPrecoAnuncio);
            foto = itemView.findViewById(R.id.imageViewAnuncio);

        }
    }
}
