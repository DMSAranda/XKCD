package com.example.tarea3.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.core.os.HandlerCompat;
import com.example.tarea3.database.ComicDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class App extends Application {

    ExecutorService diskIOExecutor = Executors.newSingleThreadExecutor();   //ejecutor para hilo de acceso a disco
    ComicDatabase db = new ComicDatabase(this);


    @Override
    public void onCreate() {

        super.onCreate();


        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    private class MainThreadExecutor implements Executor {

        private final Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {

            mainThreadHandler.post(runnable);
        }
    }
}
