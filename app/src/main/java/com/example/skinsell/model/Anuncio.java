package com.example.skinsell.model;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skinsell.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.List;

public class Anuncio implements Serializable {
    private String idAnuncio;
    private String arma;
    private String categoria;
    private String stattrack;
    private String titulo;
    private String preco;
    private String telefone;
    private String descricao;
    private List<String> fotos;


    public Anuncio(){
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("meus_anuncios");
        setIdAnuncio(anuncioRef.push().getKey());
    }

    public void salvar(){

        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("meus_anuncios");

        anuncioRef.child(idUsuario)
                .child(getIdAnuncio())
                .setValue(this);

        salvarAnuncioPublico();
    }

    public void salvarAnuncioPublico(){


        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("anuncios");

        anuncioRef.child(getArma())
                .child(getCategoria())
                .child(getStattrack())
                .child(getIdAnuncio())
                .setValue(this);
    }

    public void remover(){
        String idUsuario = ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("meus_anuncios")
                .child(idUsuario)
                .child(getIdAnuncio());

        anuncioRef.removeValue();
        removerAnuncioPublico();
    }

    public void removerAnuncioPublico(){
        DatabaseReference anuncioRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("anuncios")
                .child(getArma())
                .child(getCategoria())
                .child(getStattrack())
                .child(getIdAnuncio());

        anuncioRef.removeValue();

    }



    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getArma() {
        return arma;
    }

    public void setArma(String arma) {
        this.arma = arma;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStattrack() {
        return stattrack;
    }

    public void setStattrack(String stattrack) {
        this.stattrack = stattrack;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
