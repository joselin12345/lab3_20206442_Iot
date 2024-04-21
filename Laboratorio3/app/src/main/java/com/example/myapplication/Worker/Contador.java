package com.example.myapplication.Worker;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.myapplication.R;
import com.example.myapplication.dto.Primo;
import com.example.myapplication.service.PrimoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Contador extends Worker {

    private PrimoService primoService;
    private TextView primoNumero;


    public Contador(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {

        // Consulta
        primoService = new Retrofit.Builder()
                .baseUrl("https://prime-number-api.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PrimoService.class);
        // Lista
        primoService.getListaPrimos().enqueue(new Callback<List<Primo>>() {

            @Override
            public void onResponse(Call<List<Primo>> call, Response<List<Primo>> response) {
                Log.d("msg-test-ws-profile","entro en la respuesta " );
                if(response.isSuccessful()){
                    List<Primo> listaPrimos = response.body();
                    int suma = 0;
                    for (Primo primo : listaPrimos) {
                        suma += primo.getNumber();
                        Log.d("msg-test-suma", "Suma actual: " + suma);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            //throw new RuntimeException(e);
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

        return Result.success();
    }


}
