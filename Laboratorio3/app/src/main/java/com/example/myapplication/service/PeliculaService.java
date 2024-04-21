package com.example.myapplication.service;

import com.example.myapplication.dto.Peliculas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PeliculaService {

    @GET("/?apikey=bf81d461")
    Call<Peliculas> getPeliculas(@Query("i") String id);


}
