package com.example.tarea3.api;

import com.example.tarea3.database.Comic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface XkcdService {

    //Esta interface recibe un numero que se añade a la llamada por get de la url de xkcd para descargar el json del comic
    @GET("{num}/info.0.json")
    Call<Comic> getComic(@Path("num") int num);
}
