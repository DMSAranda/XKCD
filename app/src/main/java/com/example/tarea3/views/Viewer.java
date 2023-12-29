package com.example.tarea3.views;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tarea3.R;
import com.example.tarea3.api.FinderAPI;
import com.example.tarea3.database.Comic;
import com.example.tarea3.database.ComicDatabase;
import com.example.tarea3.database.FinderBD;
import com.example.tarea3.main.App;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

//clase para la Activity de visor de comic "Viewer"
public class Viewer extends AppCompatActivity {

    private ComicDatabase db;
    private Executor executorBD;
    public TextView title;
    public TextView day;
    public TextView month;
    public TextView year;
    public ImageView image;
    private AtomicInteger num;
    private Comic comic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //capturamos la vista "Viewer"
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "use on create in Viewer");
        setContentView(R.layout.viewer);

        //capturamos todos los campos textview y la imagen
        title = findViewById(R.id.titleViewer);
        day = findViewById(R.id.dayViewer);
        month = findViewById(R.id.monthViewer);
        year = findViewById(R.id.yearViewer);
        image = findViewById(R.id.image);


        String id = getIntent().getStringExtra("num");   //capturamos el valor recibido por el selector y lo metemos en el valor id
        num = new AtomicInteger(Integer.parseInt(id)); //lo pasamos a numero para usarlo con la base de datos o API

        db =  ((App) getApplication()).db;
        executorBD = ((App) getApplication()).diskIOExecutor;

        Button next = findViewById(R.id.next);          //CAPTURAMOS EL BOTON next DE LA VISTA
        next.setOnClickListener(view -> {             //FUNCION DE FLECHA PARA EL CLICK

            //si llegamos al ultimo comic y pulsamos en siguiente nos avisa
            if(num.get() >= 2722){
                Toast.makeText(this, "It's the last!", Toast.LENGTH_SHORT).show();
            }
            else {
                //en caso contrario aumentamos el numero de comic y hacemos la busqueda en la base de datos o API
                num.incrementAndGet();

                callBD();


            }
        });

        Button previous = findViewById(R.id.previous);          //CAPTURAMOS EL BOTON comic DE LA VISTA
        previous.setOnClickListener(view -> {             //FUNCION DE FLECHA PARA EL CLICK

            //si llegamos al primer comic y pulsamos en anterior nos avisa
            if(num.get() <= 1){
                Toast.makeText(this, "It's the first!", Toast.LENGTH_SHORT).show();
            }
            else {
                //en caso contrario reducimos el numero de comic y hacemos la busqueda en la base de datos o API
                num.decrementAndGet();

                callBD();

            }
        });

       //busqueda en la base de datos o API

       callBD();




    }

    //metodo que ejecuta un hilo que hace busquedas en base de datos en hilo secundario en la base de datos o asincronamente en la API con retrofit
    public void callBD(){

        executorBD.execute(new Runnable() {
            @Override
            public void run() {

                //nos traemos el comic de la base de datos y si no existe viene con valor null
                comic = FinderBD.findBD(num.get(), db);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //en el hilo principal si el comic existe rellenamos los campos de la vista con los datos que hemos recuperado dentro de objeto comic
                        if (comic != null) {
                            title.setText(comic.getTitle());
                            day.setText(comic.getDay());
                            month.setText(comic.getMonth());
                            year.setText(comic.getYear());
                            //usamos picasso para cargar la imagen de cache forzando la opcion offline
                            Picasso.get()
                                    .load(comic.getImg())
                                    .placeholder(R.drawable.loading)
                                    .networkPolicy(NetworkPolicy.OFFLINE)
                                    .into(image);
                        }
                        //si no existe hacemos una llamada al metodo callRetrofir que hace una llamada asincrona a retrofit para descargar el json con los datos
                        else{
                            callRetrofit();
                        }
                    }
                });
            }
        });


    }


    public void callRetrofit(){

        //ponemos en el titulo la palabra "descargando" y con la llamada asincrona a retrofit
        // vamos a rellenar en el hilo principal en la vista los campos del comic que descargamos

        if (comic == null){
            title.setText("Downloading");
            FinderAPI.retrofitCall(num.get(), title, day, month, year, image, db);
        }

    }





    @Override
    protected void onStart() {                                      //LOG DE INICIO
        super.onStart();
        Log.d("Lifecycle", "use on start in Viewer");

    }

    @Override
    protected void onPause() {                                      //LOG DE PAUSA
        super.onPause();
        Log.d("Lifecycle", "use on pause in Viewer");
    }

    @Override
    protected void onStop() {                                        //LOG DE PARADA
        super.onStop();
        Log.d("Lifecycle", "use on stop in Viewer");
    }

    @Override
    protected void onResume() {                                         //LOG DE inicio de actividad
        super.onResume();
        Log.d("Lifecycle", "use on resume in Viewer");
    }

    @Override
    protected void onDestroy() {                                    //LOG DE destruccion de activity
        super.onDestroy();
        Log.d("Lifecycle", "use on destroy in Viewer");
    }



}


