package com.example.skinsell.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skinsell.R;
import com.example.skinsell.config.Shared;
import com.example.skinsell.model.Anuncio;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.NumberFormat;
import java.util.Locale;

public class DetalhesSkinsActivity extends AppCompatActivity {

    private CarouselView carouselView;
    private TextView titulo;
    private TextView descricao;
    private TextView preco;
    private TextView categoria;
    private TextView stattrack;
    private Anuncio anuncioSelecionado;
    private Button abrirTelefone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_skins);

        //Toolbar
        getSupportActionBar().setTitle("Detalhe Skins");



        inicializarComponentes();

        abrirTelefone.setOnClickListener(view -> {
             { Shared.openWhatsApp(this, anuncioSelecionado.getTelefone(),"Testando"); }
        });

        //Recuperar anuncio para exibição
        anuncioSelecionado = (Anuncio) getIntent().getSerializableExtra("anuncioSelecionado");

        NumberFormat precoFormat = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));

        if (anuncioSelecionado != null){
            titulo.setText(anuncioSelecionado.getTitulo());
            descricao.setText(anuncioSelecionado.getDescricao());
            categoria.setText(anuncioSelecionado.getCategoria());
            stattrack.setText(anuncioSelecionado.getStattrack());
            preco.setText(precoFormat.format(Double.parseDouble(anuncioSelecionado.getPreco())));

            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    String urlString = anuncioSelecionado.getFotos().get(position);
                    Picasso.get().load(urlString).into(imageView);
                }
            };

            carouselView.setPageCount(anuncioSelecionado.getFotos().size());
            carouselView.setImageListener(imageListener);
        }
    }

    private void abrirTelefone(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", anuncioSelecionado.getTelefone(), null));
        startActivity(intent);
    }

    private void inicializarComponentes(){
        carouselView = findViewById(R.id.carouselView);
        titulo = findViewById(R.id.textTituloDetalhe);
        descricao = findViewById(R.id.textDescricaoDetalhe);
        preco = findViewById(R.id.textPrecoDetalhe);
        categoria = findViewById(R.id.textCategoriaDetalhe);
        stattrack = findViewById(R.id.textStatTrackDetalhe);
        abrirTelefone = findViewById(R.id.botaoVerTelefone);
    }
}