package com.example.skinsell.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skinsell.R;
import com.example.skinsell.adapter.AdapterAnuncio;
import com.example.skinsell.config.ConfiguracaoFirebase;
import com.example.skinsell.model.Anuncio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class AnunciosActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private RecyclerView recyclerViewAnuncios;
    private FloatingActionButton botaoAdcionarAnuncio;
    private Button botaoArmas, botaoCategoria, botaoTipo;
    private AdapterAnuncio adapterAnuncio;
    private List<Anuncio> listAnuncios = new ArrayList<>();
    private DatabaseReference anunciosPublicosRef;
    private AlertDialog dialog;
    private String filtroArmas = "";
    private String filtroCategoria = "";
    private String filtroTipo = "";
    private boolean filtrandoPorArmas = false;
    private boolean filtrandoPorCategoria = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        inicializarComponentes();

        recyclerViewAnuncios.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnuncios.setHasFixedSize(true);
        adapterAnuncio = new AdapterAnuncio(listAnuncios, this);
        recyclerViewAnuncios.setAdapter(adapterAnuncio);



        //Configurações iniciais
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        verificarUsuario();

        anunciosPublicosRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("anuncios");


        recuperarAnunciosPublicos();



        botaoAdcionarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastrarAnuncioActivity.class));
            }
        });

    }

    public void filtrarPorArmas(View view){
        AlertDialog.Builder dialogArma = new AlertDialog.Builder(this);
        dialogArma.setTitle("Selecione a arma desejada");

        //Spinner
        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        //Spinner armas
        Spinner  spinnerArmas = viewSpinner.findViewById(R.id.spinnerFiltro);
        String[] armas = getResources().getStringArray(R.array.armas);
        ArrayAdapter<String> adapterArmas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armas);
        adapterArmas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArmas.setAdapter(adapterArmas);
        dialogArma.setView(viewSpinner);

        dialogArma.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                filtroArmas = spinnerArmas.getSelectedItem().toString();
                recuperarAnunciosPorArmas();
                filtrandoPorArmas = true;
            }
        });

        dialogArma.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = dialogArma.create();
        dialog.show();
    }


    public void filtrarPorCategoria(View view){

        if (filtrandoPorArmas == true){
            AlertDialog.Builder dialogArma = new AlertDialog.Builder(this);
            dialogArma.setTitle("Selecione a pintura da arma");

            //Spinner
            View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

            //Spinner categorias
            Spinner  spinnerCategoria = viewSpinner.findViewById(R.id.spinnerFiltro);
            String[] armas = getResources().getStringArray(R.array.categoria);
            ArrayAdapter<String> adapterArmas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armas);
            adapterArmas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategoria.setAdapter(adapterArmas);
            dialogArma.setView(viewSpinner);

            dialogArma.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    filtroCategoria = spinnerCategoria.getSelectedItem().toString();
                    recuperarAnunciosPorCategoria();
                    filtrandoPorCategoria = true;
                }
            });

            dialogArma.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = dialogArma.create();
            dialog.show();
        }else {
            Toast.makeText(this, "Primeiro escolha a arma", Toast.LENGTH_SHORT).show();
        }


    }

    public void filtrarPorTipo(View view){

        if (filtrandoPorCategoria == true){
            AlertDialog.Builder dialogArma = new AlertDialog.Builder(this);
            dialogArma.setTitle("Selecione o tipo da arma");

            //Spinner
            View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

            //Spinner stattrack
            Spinner  spinnerStatrack = viewSpinner.findViewById(R.id.spinnerFiltro);
            String[] armas = getResources().getStringArray(R.array.stattrack);
            ArrayAdapter<String> adapterArmas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armas);
            adapterArmas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerStatrack.setAdapter(adapterArmas);
            dialogArma.setView(viewSpinner);

            dialogArma.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    filtroTipo = spinnerStatrack.getSelectedItem().toString();
                    recuperarAnunciosPorTipo();
                }
            });

            dialogArma.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog dialog = dialogArma.create();
            dialog.show();
        }else {
            Toast.makeText(this, "Primeiro escolha a categoria", Toast.LENGTH_SHORT).show();
        }


    }



    public void recuperarAnunciosPorArmas(){

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();

        //Configura nó por estado
        anunciosPublicosRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("anuncios")
                .child(filtroArmas);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAnuncios.clear();
                for (DataSnapshot categorias: snapshot.getChildren()){
                    for (DataSnapshot stattrack: categorias.getChildren()){
                        for (DataSnapshot anuncios: stattrack.getChildren()){
                            Anuncio anuncio = anuncios.getValue(Anuncio.class);
                            listAnuncios.add(anuncio);

                        }
                    }
                }

                Collections.reverse(listAnuncios);
                adapterAnuncio.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recuperarAnunciosPorCategoria(){

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();

        //Configura nó por estado
        anunciosPublicosRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("anuncios")
                .child(filtroArmas)
                .child(filtroCategoria);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAnuncios.clear();
                for (DataSnapshot stattrack: snapshot.getChildren()){
                    for (DataSnapshot anuncios: stattrack.getChildren()){
                        Anuncio anuncio = anuncios.getValue(Anuncio.class);
                        listAnuncios.add(anuncio);

                    }
                }

                Collections.reverse(listAnuncios);
                adapterAnuncio.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recuperarAnunciosPorTipo(){

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();

        //Configura nó por tipo
        anunciosPublicosRef = ConfiguracaoFirebase.getFirebaseDatabase()
                .child("anuncios")
                .child(filtroArmas)
                .child(filtroCategoria)
                .child(filtroTipo);

        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAnuncios.clear();
                for (DataSnapshot anuncios: snapshot.getChildren()){
                    Anuncio anuncio = anuncios.getValue(Anuncio.class);
                    listAnuncios.add(anuncio);

                }

                Collections.reverse(listAnuncios);
                adapterAnuncio.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void recuperarAnunciosPublicos(){
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Anúncios")
                .setCancelable(false)
                .build();
        dialog.show();

        listAnuncios.clear();
        anunciosPublicosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot armas:snapshot.getChildren()){
                    for (DataSnapshot categorias: armas.getChildren()){
                        for (DataSnapshot stattrack: categorias.getChildren()){
                            for (DataSnapshot anuncios: stattrack.getChildren()){
                                Anuncio anuncio = anuncios.getValue(Anuncio.class);
                                listAnuncios.add(anuncio);

                            }
                        }
                    }
                }

                Collections.reverse(listAnuncios);
                adapterAnuncio.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                verificarUsuario();
                break;
            case R.id.menu_anuncios:
                startActivity(new Intent(getApplicationContext(), MeusAnunciosActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void inicializarComponentes(){
        botaoAdcionarAnuncio = findViewById(R.id.floatingActionButtonAnuncio);
        recyclerViewAnuncios = findViewById(R.id.recyclerViewAnuncios);
        botaoArmas = findViewById(R.id.botaoArmas);
        botaoCategoria = findViewById(R.id.botaoCategoria);
        botaoTipo = findViewById(R.id.botaoSattrack);
    }
}