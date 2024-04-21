package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.myapplication.Worker.Contador;
import com.example.myapplication.dto.Peliculas;
import com.example.myapplication.dto.Primo;
import com.example.myapplication.service.PeliculaService;
import com.example.myapplication.service.PrimoService;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContadorActivity extends AppCompatActivity {

    private ConstraintLayout regresar;
    private EditText editTextText;
    private ConstraintLayout buscar;
    private PrimoService primoService;
    private TextView primoNumero;
    private TextView textView10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.contador);
        showToastWithDelay("Contador de primos", 3000);

        // Boton regresar
        regresar = findViewById(R.id.regresar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContadorActivity.this,OpcionesActivity.class));
            }
        });

        // Consulta
        primoService = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrimoService.class);

        // Buscar primo por ID
        buscar =  findViewById(R.id.buscar);
        editTextText = findViewById(R.id.editTextText);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = editTextText.getText().toString().trim();
                if (!userInput.isEmpty()) {
                    fetchProfileFromWs(Integer.parseInt(userInput));
                } else {
                    Toast.makeText(ContadorActivity.this, "Por favor, ingrese algo antes de continuar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Cambio de botones, se uso ayuda de Chatgpt:
        TextView textView24 = findViewById(R.id.textView24);
        ConstraintLayout constraintLayout3 = findViewById(R.id.constraintLayout3);
        ConstraintLayout constraintLayout2 = findViewById(R.id.constraintLayout2);
        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (constraintLayout3.getVisibility() == View.VISIBLE) {
                    constraintLayout3.setVisibility(View.GONE);
                    textView24.setVisibility(View.VISIBLE);
                    textView10 = findViewById(R.id.textView10);
                    textView10.setText("Reinicio");
                    //pausa el contador
                } else {
                    constraintLayout3.setVisibility(View.VISIBLE);
                    textView24.setVisibility(View.GONE);
                    textView10 = findViewById(R.id.textView10);
                    textView10.setText("Pausa");
                    //inica contador
                }
            }
        });

        //Contador

        // iniciarContador(); i
    }

    public void fetchProfileFromWs(int id){
        Log.d("msg-test-ws-profile","entra al metodo " );

        primoService.getListaPrimos().enqueue(new Callback<List<Primo>>() {

            @Override
            public void onResponse(Call<List<Primo>> call, Response<List<Primo>> response) {
                Log.d("msg-test-ws-profile","entro en la respuesta " );
                if(response.isSuccessful()){
                    List<Primo> listaPrimos = response.body();
                    Log.d("msg-test-ws-profile",String.valueOf(listaPrimos.get(0).getOrder()) );
                    if (listaPrimos != null && !listaPrimos.isEmpty()) {
                        Log.d("msg-test-ws-profile",String.valueOf(listaPrimos.get(0).getOrder()) );
                        for (Primo primo : listaPrimos) {
                            if (primo.getOrder() == id){
                                Log.d("msg-test-ws-profile",String.valueOf(primo.getOrder()) );
                                Log.d("msg-test-ws-profile",String.valueOf(primo.getNumber()) );
                                primoNumero = findViewById(R.id.primoNumero);
                                primoNumero.setText(String.valueOf(primo.getNumber()));
                                break;
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Primo>> call, Throwable t) {
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

    private void iniciarContador() {
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(Contador.class).build();

        WorkManager
                .getInstance(ContadorActivity.this.getApplicationContext())
                .enqueue(workRequest);

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(workRequest.getId())
                .observe(this, workInfo -> {
                    if (workInfo != null ) {
                        Data progress = workInfo.getProgress();
                        int contador = progress.getInt("contador", 0);
                        Log.d("msg-test", "progress: " + contador);
                    } else  {

                    }
                });


    }

}
