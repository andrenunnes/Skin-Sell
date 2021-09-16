package com.example.skinsell.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.skinsell.R;

public class MainActivity extends AppCompatActivity {

    private TextView jatenhocadastro;
    private Button buttoncadastroInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        jatenhocadastro = findViewById(R.id.textView_tenho_cadastro);
        buttoncadastroInicio = findViewById(R.id.button_cadastro_inicio);

        jatenhocadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


        buttoncadastroInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
            }
        });
    }


}