package com.example.skinsell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.skinsell.R;
import com.example.skinsell.adapter.AdapterAnuncios;
import com.example.skinsell.config.ConfiguracaoFirebase;
import com.example.skinsell.model.Anuncio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class AnunciosActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private RecyclerView recyclerViewAnuncio;
    private FloatingActionButton botaoAdcionarAnuncio;
    private List<Anuncio> listaAnuncios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        botaoAdcionarAnuncio = findViewById(R.id.floatingActionButtonAnuncio);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        verificarUsuario();
        //autenticacao.signOut();

        recyclerViewAnuncio = findViewById(R.id.recyclerViewAnuncios);

        //ListaAnuncios
        this.criarAnuncios();

        //Adapter
        AdapterAnuncios adapterAnuncios = new AdapterAnuncios(listaAnuncios);

        //Recycle
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAnuncio.setLayoutManager(layoutManager);
        recyclerViewAnuncio.setHasFixedSize(true);
        recyclerViewAnuncio.setAdapter(adapterAnuncios);

        botaoAdcionarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastrarAnuncioActivity.class));
            }
        });

    }

    public void criarAnuncios(){
        Anuncio anuncio = new Anuncio("https://biografiaresumida.com.br/wp-content/uploads/2017/11/007ESQ_Fabio_Lago_002-768x433.jpg", "bota", "300.00");
        this.listaAnuncios.add(anuncio);
    }

    public void verificarUsuario(){
        if (autenticacao.getCurrentUser() == null){
            botaoAdcionarAnuncio.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (autenticacao.getCurrentUser() == null){
            menu.setGroupVisible(R.id.group_deslogado, true);
        }else{
            menu.setGroupVisible(R.id.group_logado, true);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_cadastrar:
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
                break;
            case R.id.menu_entrar:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            case R.id.menu_sair:
                autenticacao.signOut();
                invalidateOptionsMenu();
                break;
            case R.id.menu_anuncios:
                startActivity(new Intent(getApplicationContext(), MeusAnunciosActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}