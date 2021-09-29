package com.example.skinsell.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.skinsell.R;
import com.example.skinsell.helper.Permissao;

import java.util.ArrayList;
import java.util.List;

public class CadastrarAnuncioActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText campoTitulo, campoPreco, campoTelefone, campoDescricao;
    private Button buttonCadastroAnuncio;
    private Spinner campoArma, campoCategoria, campoStatTrack;

    private ImageView imagem1, imagem2, imagem3;

    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private List<String> listaFotosRecuperadas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anuncio);

        //Validar permissoes
        Permissao.validarPermissoes(permissoes, this, 1);

        inicializarComponentes();

        carregarDadosSpinner();

        buttonCadastroAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void validarDadosAnuncio(View view){
        String arma = campoArma.getSelectedItem().toString();
        String categoria = campoCategoria.getSelectedItem().toString();
        String stattrack = campoStatTrack.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();
        String preco = campoPreco.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String fone = campoTelefone.getText().toString();
        String descricao = campoDescricao.getText().toString();

        if (listaFotosRecuperadas.size() != 0){
            if (!arma.isEmpty()){
                if (!categoria.isEmpty()){
                    if (!stattrack.isEmpty()){
                        if (!titulo.isEmpty()){
                            if (!preco.isEmpty()){
                                if (!telefone.isEmpty() && fone.length() >= 10){
                                    if (!descricao.isEmpty()){

                                    }else {
                                        exibirMensagemErro("Preencha a descrição!");
                                    }
                                }else {
                                    exibirMensagemErro("Preencha o campo telefone, digite ao menos dez números!");
                                }
                            }else {
                                exibirMensagemErro("Preencha o campo preço!");
                            }
                        }else {
                            exibirMensagemErro("Preencha o campo título!");
                        }
                    }else {
                        exibirMensagemErro("Selecione uma categoria!");
                    }
                }else {
                    exibirMensagemErro("Selecione o tipo da arma!");
                }
            }else {
                exibirMensagemErro("Selecione uma arma!");
            }
        }else {
            exibirMensagemErro("Selecione ao menos uma foto!");
        }

    }

    private void exibirMensagemErro(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    public void salvarAnuncio(){
        String preco =  campoPreco.getText().toString();
        String titulo = campoTitulo.getText().toString();
        String telefone = campoTelefone.getText().toString();
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

    private void inicializarComponentes(){

        campoTitulo = findViewById(R.id.editText_titulo);
        campoPreco = findViewById(R.id.editText_preco);
        campoTelefone = findViewById(R.id.editText_telefone);
        campoDescricao = findViewById(R.id.editText_descricao);
        buttonCadastroAnuncio = findViewById(R.id.button_cadastrarAnuncio);

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults){
            if (permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessários aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}