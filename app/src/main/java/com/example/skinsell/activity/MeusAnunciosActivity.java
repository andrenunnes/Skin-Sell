package com.example.skinsell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.skinsell.R;
import com.example.skinsell.adapter.AdapterAnuncio;
import com.example.skinsell.config.ConfiguracaoFirebase;
import com.example.skinsell.model.Anuncio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeusAnunciosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMeusAnuncios;
    private List<Anuncio> anuncios = new ArrayList<>();
    private AdapterAnuncio adapterAnuncio;
    private DatabaseReference anuncioUuarioRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        //Configurações iniciais
        anuncioUuarioRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("meus_anuncios")
                .child(ConfiguracaoFirebase.getIdUsuario());

        inicializarComponentes();


        FloatingActionButton fab = findViewById(R.id.botaoCadastrar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastrarAnuncioActivity.class ));
            }
        });

        recyclerViewMeusAnuncios.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMeusAnuncios.setHasFixedSize(true);
        adapterAnuncio = new AdapterAnuncio(anuncios, this);
        recyclerViewMeusAnuncios.setAdapter(adapterAnuncio);

        //Recupera o anuncio para o usuario
        recuperarAnuncio();



    }

    private void recuperarAnuncio(){
        anuncioUuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                anuncios.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    anuncios.add(ds.getValue(Anuncio.class));
                }
                Collections.reverse(anuncios);
                adapterAnuncio.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void inicializarComponentes(){
        recyclerViewMeusAnuncios = findViewById(R.id.recyclerViewMeusAnuncios);
    }
}