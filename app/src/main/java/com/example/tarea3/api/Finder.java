package com.example.tarea3.api;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import com.example.tarea3.app.Viewer;
import com.example.tarea3.database.Comic;
import com.example.tarea3.database.ComicDatabase;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Finder implements Runnable{

    private final int num;
    private final ComicDatabase db;
    private final Viewer context;



    public Finder(int num, ComicDatabase db, Viewer context){
        this.num = num;
        this.db = db;
        this.context = context;

    }

    @SuppressLint("SetTextI18n")
        @Override
        public void run() {

            Comic comic = db.readComic(num);


            if(comic == null){
                // Todavía no lo tenemos lo mostramos de retrofit

                context.title.setText("Downloading...");

                retrofitCall(num, comic);

            }

            else{
                // Lo mostramos de base de datos


                        context.title.setText(comic.getTitle());
                        context.day.setText(comic.getDay());
                        context.month.setText(comic.getMonth());
                        context.year.setText(comic.getYear());

                        Picasso.get()
                                .load(comic.getImg())
                                .networkPolicy(NetworkPolicy.OFFLINE)
                                .into(context.image);

            }

        }

    public void retrofitCall(int num, Comic comic) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        XkcdService xkcdService = retrofit.create(XkcdService.class);

        Call<Comic> request = xkcdService.getComic(num);

        request.enqueue(new Callback<Comic>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Comic> call, @NonNull Response<Comic> response) {

                Comic comic2 = response.body();
                assert comic2 != null;
                context.title.setText(comic2.getTitle());
                context.day.setText(comic2.getDay());
                context.month.setText(comic2.getMonth());
                context.year.setText(comic2.getYear());

                Picasso.get()
                        .load(comic2.getImg())
                    //    .error(R.drawable.error)
                    //    .placeholder(R.drawable.placeholder)
                        .into(context.image);


                if (comic == null) {
                    db.createComic(comic2);
                }
            }

            @Override
            public void onFailure(Call<Comic> call, Throwable t) {

                //SI FALLA ERROR DE DESCARGA (MODO AVION)

            }
        });

    }



}

