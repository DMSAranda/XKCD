package com.example.tarea3.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.tarea3.R;
import com.example.tarea3.database.Comic;
import com.example.tarea3.database.ComicDatabase;
import com.example.tarea3.views.Viewer;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinderAPI {

        //En esta clase tenemos un metodo retrofitCall que recibe el numero, titulo, fecha, la url de la imagen y la base de datos

       public static void retrofitCall(int num, TextView title, TextView day, TextView month, TextView year, ImageView image, ComicDatabase db) {

        //creamos la instancia de retrofit con la url de la web de xkcd
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://xkcd.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //creamos la instancia de la interfaz implementando retrofit
        XkcdService xkcdService = retrofit.create(XkcdService.class);

        //creamos la llamada que va a hacer retrofit con el numero de comic que enviamos
        Call<Comic> request = xkcdService.getComic(num);

        //creamos la llamada de callback con los datos que pasamos desde la vista
        request.enqueue(new Callback<Comic>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Comic> call, Response<Comic> response) {

                //metemos la respuesta de la llamada en un obojeto comic
                Comic comic = response.body();
                //rellenamos los campos de la vista con los datos del objeto comic devueltos por la llmada de retrofit
                title.setText(comic.getTitle());
                day.setText(comic.getDay());
                month.setText(comic.getMonth());
                year.setText(comic.getYear());
                //utilizamos la libreria de picasso para pintar la imagen a traves de la url
                Picasso.get()
                        .load(comic.getImg())
                        //imagen mientras se hace la descarga
                        .placeholder(R.drawable.downloading)
                        //imagen con error
                        .error(R.drawable.error)
                        //forzamos que no haya cache, ni se use memoria
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        //lo pintamos en el espacio de la imagen de la vista
                        .into(image);
                //tras descargarlo insertamos el objeto comic descargado en la base de datos
                db.createComic(comic);
            }

            //si falla la descarga
            @Override
            public void onFailure(Call<Comic> call, Throwable t) {
                //utilizamos la libreria de picasso para pintar la imagen del error
                Picasso.get()
                        .load(image.toString())
                        //imagen con error
                        .error(R.drawable.error)
                        .into(image);


            }
        });


    }

}

