package com.example.skinsell.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.skinsell.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MeusAnunciosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_anuncios);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastrarAnuncioActivity.class ));
            }
        });

        recyclerViewAnuncios = findViewById(R.id.recyclerViewMeusAnuncios);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAnuncios.setLayoutManager(layoutManager);
        recyclerViewAnuncios.setHasFixedSize(true);


    }
}