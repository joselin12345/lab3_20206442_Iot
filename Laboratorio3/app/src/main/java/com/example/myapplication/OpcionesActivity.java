package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.dto.Peliculas;
import com.example.myapplication.service.PeliculaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpcionesActivity extends AppCompatActivity {

    private ConstraintLayout buscar;
    private ConstraintLayout visualizar;
    private ConstraintLayout regresar;
    private EditText editTextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.opciones);
        showToastWithDelay("Pagina de opciones", 3000);

        buscar =  findViewById(R.id.buscar);
        visualizar = findViewById(R.id.visualizar);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpcionesActivity.this,DetallesActivity.class));
            }
        });

        visualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpcionesActivity.this, ContadorActivity.class));
            }
        });


        regresar = findViewById(R.id.regresar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OpcionesActivity.this,MainActivity.class));
            }
        });


        buscar =  findViewById(R.id.buscar);
        editTextText = findViewById(R.id.editTextText);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editTextText.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    Intent intent = new Intent(OpcionesActivity.this, DetallesActivity.class);
                    intent.putExtra("USER_INPUT", userInput);
                    startActivity(intent);
                } else {
                    Toast.makeText(OpcionesActivity.this, "Por favor, ingrese algo antes de continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    // Toast dura 5 segundos
    private void showToastWithDelay(String message, int duration) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, duration);
    }

}
