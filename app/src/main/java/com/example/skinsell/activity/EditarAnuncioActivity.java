package com.example.skinsell.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.skinsell.R;
import com.example.skinsell.config.Mask;
import com.example.skinsell.model.Anuncio;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditarAnuncioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText campoTitulo, campoPreco, campoTelefone, campoDescricao;
    private Button buttonCadastroAnuncio;
    private Spinner campoArma, campoCategoria, campoStatTrack;
    private Anuncio anuncio;
    private StorageReference firebaseStorage;
    private android.app.AlertDialog dialog;

    private ImageView imagem1, imagem2, imagem3;

    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_anuncio);

        inicializarComponentes();
        carregarDadosSpinner();

        //Recuperar anuncio para exibição
        Anuncio anuncioSelecionado = (Anuncio) getIntent().getSerializableExtra("anuncioSelecionado");
        if (anuncioSelecionado != null){
            atualizandoDadosAnuncio(anuncioSelecionado);
        }
    }

    private void atualizandoDadosAnuncio(Anuncio anuncio){
        campoDescricao.setText(anuncio.getDescricao());
        campoPreco.setText(anuncio.getPreco());
        campoTelefone.setText(anuncio.getTelefone());
        campoTitulo.setText(anuncio.getTitulo());

        String[] armas = getResources().getStringArray(R.array.armas);
        ArrayList listaArmas = new ArrayList<String>(Arrays.asList(armas));
        if (listaArmas.indexOf(anuncio.getArma()) >= 0 ){
            campoArma.setSelection(listaArmas.indexOf(anuncio.getArma()));
        }

        String[] categoria = getResources().getStringArray(R.array.categoria);
        ArrayList listaCategoria = new ArrayList<String>(Arrays.asList(categoria));
        if (listaCategoria.indexOf(anuncio.getCategoria()) >= 0 ){
            campoCategoria.setSelection(listaCategoria.indexOf(anuncio.getCategoria()));
        }

        String[] stattrack = getResources().getStringArray(R.array.stattrack);
        ArrayList listaStattrack = new ArrayList<String>(Arrays.asList(stattrack));
        if (listaStattrack.indexOf(anuncio.getStattrack()) >= 0 ){
            campoStatTrack.setSelection(listaStattrack.indexOf(anuncio.getStattrack()));
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageCadastro1:
                escolherImagem(1);
                break;
            case R.id.imageCadastro2:
                escolherImagem(2);
                break;
            case R.id.imageCadastro3:
                escolherImagem(3);
                break;
        }
    }

    public void escolherImagem(int requestCode){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            //Recuperar imagem
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            //Configura imagem no ImageView
            if (requestCode == 1){
                imagem1.setImageURI(imagemSelecionada);
            }else if (requestCode == 2){
                imagem2.setImageURI(imagemSelecionada);
            }else if (requestCode == 3){
                imagem3.setImageURI(imagemSelecionada);
            }

            listaFotosRecuperadas.add(caminhoImagem);
        }
    }

    private void carregarDadosSpinner(){
        //Spinner de armas
        String[] armas = getResources().getStringArray(R.array.armas);
        ArrayAdapter<String> adapterArmas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, armas);
        adapterArmas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoArma.setAdapter(adapterArmas);

        //Spinner categorias
        String[] categorias = getResources().getStringArray(R.array.categoria);
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categorias);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoCategoria.setAdapter(adapterCategoria);

        //Spinner stattrack
        String[] stattrack = getResources().getStringArray(R.array.stattrack);
        ArrayAdapter<String> adapterStatTrack = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stattrack);
        adapterStatTrack.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoStatTrack.setAdapter(adapterStatTrack);

    }

    private void inicializarComponentes(){

        campoTitulo = findViewById(R.id.editText_titulo);
        campoPreco = findViewById(R.id.editText_preco);
        campoTelefone = findViewById(R.id.editText_telefone);
        campoDescricao = findViewById(R.id.editText_descricao);
        buttonCadastroAnuncio = findViewById(R.id.button_cadastrarAnuncio);

        campoTelefone.addTextChangedListener(Mask.insert("(##)#####-####", campoTelefone));


        campoArma = findViewById(R.id.spinnerArma);
        campoCategoria = findViewById(R.id.spinnerCategoria);
        campoStatTrack = findViewById(R.id.spinnerStatTrak);

        imagem1 = findViewById(R.id.imageCadastro1);
        imagem2 = findViewById(R.id.imageCadastro2);
        imagem3 = findViewById(R.id.imageCadastro3);
        imagem1.setOnClickListener(this);
        imagem2.setOnClickListener(this);
        imagem3.setOnClickListener(this);


    }
}