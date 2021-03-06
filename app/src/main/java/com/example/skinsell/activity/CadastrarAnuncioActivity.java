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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.skinsell.R;
import com.example.skinsell.config.ConfiguracaoFirebase;
import com.example.skinsell.config.Mask;
import com.example.skinsell.helper.Permissao;
import com.example.skinsell.model.Anuncio;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import dmax.dialog.SpotsDialog;

public class CadastrarAnuncioActivity extends AppCompatActivity implements View.OnClickListener{

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
        setContentView(R.layout.activity_cadastrar_anuncio);

        //Configura????es iniciais
        firebaseStorage = ConfiguracaoFirebase.getFirebaseStorage();

        //Validar permissoes
        Permissao.validarPermissoes(permissoes, this, 1);

        inicializarComponentes();
        carregarDadosSpinner();



    }

    public void salvarAnuncio(){
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Salvando An??ncio")
                .setCancelable(false)
                .build();
        dialog.show();

        //Salvar imagem no Storage
        for (int i = 0; i < listaFotosRecuperadas.size(); i++){
            String urlImagem = listaFotosRecuperadas.get(i);
            int tamanhoLista = listaFotosRecuperadas.size();
            salvarFotoStorage(urlImagem, tamanhoLista, i);

        }
    }

    private void salvarFotoStorage(String urlString, int totalFotos, int contador){
        //N?? do Storage
        final StorageReference imagemAnuncio = firebaseStorage.child("imagens")
                .child("anuncios")
                .child(anuncio.getIdAnuncio())
                .child("imagem" + contador);

        //Fazer upload

        UploadTask uploadTask = imagemAnuncio.putFile(Uri.parse(urlString));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagemAnuncio.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                       Uri firebaseUrl = task.getResult();
                       String urlConvertida = firebaseUrl.toString();

                       listaURLFotos.add(urlConvertida);
                       if (totalFotos == listaURLFotos.size()){
                           anuncio.setFotos(listaURLFotos);
                           anuncio.salvar();

                           dialog.dismiss();
                           finish();
                       }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer o upload");
                Log.i("INFO", "Falha ao fazer o upload" + e.getMessage());
            }
        });

    }

    private Anuncio configurarAnuncio(){
        String arma = campoArma.getSelectedItem().toString();
        String categoria = campoCategoria.getSelectedItem().toString();
        String stattrack = campoStatTrack.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();
        String preco = campoPreco.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String descricao = campoDescricao.getText().toString();

        Anuncio anuncio = new Anuncio();
        anuncio.setArma(arma);
        anuncio.setCategoria(categoria);
        anuncio.setStattrack(stattrack);
        anuncio.setTitulo(titulo);
        anuncio.setPreco(preco);
        anuncio.setTelefone(telefone);
        anuncio.setDescricao(descricao);

        return anuncio;
    }

    public void validarDadosAnuncio(View view){
        anuncio = configurarAnuncio();

        if (listaFotosRecuperadas.size() != 0){
            if (!anuncio.getArma().isEmpty()){
                if (!anuncio.getCategoria().isEmpty()){
                    if (!anuncio.getStattrack().isEmpty()){
                        if (!anuncio.getTitulo().isEmpty()){
                            if (!anuncio.getPreco().isEmpty()){
                                if (!anuncio.getTelefone().isEmpty() && anuncio.getTelefone().length() >= 10){
                                    if (!anuncio.getDescricao().isEmpty()){
                                        salvarAnuncio();

                                    }else {
                                        exibirMensagemErro("Preencha a descri????o!");
                                    }
                                }else {
                                    exibirMensagemErro("Preencha o campo telefone, digite ao menos dez n??meros!");
                                }
                            }else {
                                exibirMensagemErro("Preencha o campo pre??o!");
                            }
                        }else {
                            exibirMensagemErro("Preencha o campo t??tulo!");
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
        Toast.makeText(CadastrarAnuncioActivity.this, mensagem, Toast.LENGTH_SHORT).show();
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
        builder.setTitle("Permiss??es Negadas");
        builder.setMessage("Para utilizar o app ?? necess??rios aceitar as permiss??es");
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