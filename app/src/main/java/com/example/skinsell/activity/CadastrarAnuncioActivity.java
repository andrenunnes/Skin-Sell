package com.example.skinsell.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.skinsell.R;

public class CadastrarAnuncioActivity extends AppCompatActivity {

    private EditText campoTitulo, campoPreco, campoTelefone, campoDescricao;
    private Button buttonCadastroAnuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_anuncio);

        inicializarComponentes();

        buttonCadastroAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarAnuncio();
            }
        });

    }

    public void salvarAnuncio(){
        String preco =  campoPreco.getText().toString();
        Log.d("salvar", "salvarAnuncio " + preco);
        String telefone = campoTelefone.getText().toString();

    }

    private void inicializarComponentes(){

        campoTitulo = findViewById(R.id.editText_titulo);
        campoPreco = findViewById(R.id.editText_preco);
        campoTelefone = findViewById(R.id.editText_telefone);
        campoDescricao = findViewById(R.id.editText_descricao);
        buttonCadastroAnuncio = findViewById(R.id.button_cadastrarAnuncio);


    }
}