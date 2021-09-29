package com.example.skinsell.model;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skinsell.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Anuncio {
    private String foto;
    private String titulo;
    private String preco;

    public Anuncio(){

    }


    public Anuncio(String foto, String titulo, String preco) {
        this.foto = foto;
        this.titulo = titulo;
        this.preco = preco;
    }

    public Anuncio(String titulo, String preco) {
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
