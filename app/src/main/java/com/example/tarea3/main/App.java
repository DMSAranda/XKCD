package com.example.tarea3.main;

import android.app.Application;
import com.example.tarea3.database.ComicDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Clase creada para el Hilo principal
public class App extends Application {

    //creo un servicio Executor usado para las consultas a disco y una base de datos
    public ExecutorService diskIOExecutor = Executors.newSingleThreadExecutor();
    public ComicDatabase db = new ComicDatabase(this);

    //metodo on create que contiene el builder de picasso para las descargas de imagenes y las caches de estas
    @Override
    public void onCreate() {

        super.onCreate();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        //Esta opcion marca en una esquina si la descarga es via internet(rojo), disco (azul) o verde (cache)
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }


}
