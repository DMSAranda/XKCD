package com.example.tarea3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tarea3.R;
import com.example.tarea3.api.Finder;
import com.example.tarea3.database.Comic;
import com.example.tarea3.database.ComicDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;


public class Viewer extends AppCompatActivity {

    private ComicDatabase db;
    private Executor executor;
    public TextView title;
    public TextView day;
    public TextView month;
    public TextView year;
    public ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "use on create in Viewer");
        setContentView(R.layout.viewer);

        title = findViewById(R.id.titleViewer);
        day = findViewById(R.id.dayViewer);
        month = findViewById(R.id.monthViewer);
        year = findViewById(R.id.yearViewer);
        image = findViewById(R.id.image);

        String id = getIntent().getStringExtra("num");   //capturamos el valor recibido por el selector y lo metemos en el valor id
        AtomicInteger num = new AtomicInteger(Integer.parseInt(id)); //lo pasamos a numero para usarlo con la base de datos o API

        db =  ((App) getApplication()).db;
        executor = ((App) getApplication()).diskIOExecutor;


        Button next = findViewById(R.id.next);          //CAPTURAMOS EL BOTON next DE LA VISTA
        next.setOnClickListener(view -> {             //FUNCION DE FLECHA PARA EL CLICK

            if(num.get() >= 2722){
                Toast.makeText(this, "It's the last!", Toast.LENGTH_SHORT).show();
            }
            else {
                num.incrementAndGet();
                executor.execute(new Finder(num.get(), db, this));
            }
        });

        Button previous = findViewById(R.id.previous);          //CAPTURAMOS EL BOTON comic DE LA VISTA
        previous.setOnClickListener(view -> {             //FUNCION DE FLECHA PARA EL CLICK

            if(num.get() <= 1){
                Toast.makeText(this, "It's the first!", Toast.LENGTH_SHORT).show();
            }
            else {
                num.decrementAndGet();
                executor.execute(new Finder(num.get(), db, this));
            }
        });


        executor.execute(new Finder(num.get(), db, this){


        });


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


