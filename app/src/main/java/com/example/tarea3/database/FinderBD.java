package com.example.tarea3.database;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.tarea3.api.XkcdService;
import com.example.tarea3.views.Viewer;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Clase para hacer busquedas en la base de datos
public class FinderBD {

    //metodo  que recibe el numero de comic y la base de datos, hace la busqueda y le devuelve
    public static Comic findBD(int num, ComicDatabase db) {

        //
        Comic comic = db.readComic(num);

        return comic;
    }

    //metodo  que recibe la base de datos y la devuelve dentro de un ArrayList de comics
    public static List<Comic> findList(ComicDatabase db){

        List<Comic> comics = db.readList();

        return comics;
    }


}




