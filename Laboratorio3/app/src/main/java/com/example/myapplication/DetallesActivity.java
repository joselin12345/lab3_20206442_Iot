package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.myapplication.dto.Peliculas;
import com.example.myapplication.service.PeliculaService;

import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallesActivity extends AppCompatActivity {

    private ConstraintLayout regresar;
    private PeliculaService peliculaService;
    private TextView titulo;
    private TextView director;
    private TextView lenguaje;
    private TextView estreno;
    private TextView genero;
    private TextView duracion;
    private TextView trama;
    private TextView metacritic;
    private TextView tomatoes;
    private TextView internet;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detalles);
        showToastWithDelay("Detalles de una pelicula", 3000);


        String userInput = getIntent().getStringExtra("USER_INPUT");
        Log.d("msg Detalles", userInput);

        regresar = findViewById(R.id.regresar);

        checkBox = findViewById(R.id.checkBox);


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    regresar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(DetallesActivity.this,OpcionesActivity.class));
                        }
                    });
                    regresar.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.orange_button));
                } else {
                    regresar.setOnClickListener(null);
                    regresar.setBackground(null);
                }
            }
        });

        peliculaService = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PeliculaService.class);

        fetchProfileFromWs(userInput);


    }

    public void fetchProfileFromWs(String id){
        Log.d("msg-test-ws-profile","entra al metodo " );

        peliculaService.getPeliculas(id).enqueue(new Callback<Peliculas>() {

            @Override
            public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                Log.d("msg-test-ws-profile","entro en la respuesta " );
                if(response.isSuccessful()){
                    Peliculas peliculas = response.body();

                    titulo = findViewById(R.id.titulo);
                    titulo.setText(peliculas.getTitle());

                    director = findViewById(R.id.director);
                    director.setText(peliculas.getDirector());

                    lenguaje = findViewById(R.id.lenguaje);
                    lenguaje.setText(peliculas.getLenguage());

                    estreno = findViewById(R.id.estreno);
                    estreno.setText(peliculas.getReleased());

                    genero = findViewById(R.id.genero);
                    genero.setText(peliculas.getGenre());

                    duracion = findViewById(R.id.duracion);
                    duracion.setText(peliculas.getRuntime());

                    trama = findViewById(R.id.trama);
                    trama.setText(peliculas.getPlot());

                    Log.d("msg-test-ws-profile","name: " + peliculas.getRatings());

                    internet = findViewById(R.id.internet);
                    internet.setText(peliculas.getRatings().get(0).getValue());

                    metacritic = findViewById(R.id.metacritic);
                    metacritic.setText(peliculas.getRatings().get(1).getValue());

                    tomatoes = findViewById(R.id.tomatoes);
                    tomatoes.setText(peliculas.getRatings().get(2).getValue());

                    Log.d("msg-test-ws-profile","name: " + peliculas.getTitle());
                }
            }
            @Override
            public void onFailure(Call<Peliculas> call, Throwable t) {
                t.printStackTrace();
                Log.d("msg-test-ws-profile","hay un error " );
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
