package com.example.myapplication.service;

import com.example.myapplication.dto.Peliculas;
import com.example.myapplication.dto.Primo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PrimoService {

    @GET("/primeNumbers?len=999&order=1")
    Call<List<Primo>> getListaPrimos();


}
