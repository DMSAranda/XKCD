package com.example.tarea3.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.tarea3.R;
import com.example.tarea3.database.Comic;
import com.example.tarea3.database.ComicDatabase;
import com.example.tarea3.database.FinderBD;
import com.example.tarea3.main.App;

import java.util.List;
import java.util.concurrent.Executor;

//clase para la acttivity del listado de comics "Lister"
public class Lister extends AppCompatActivity {

    private ComicDatabase db;
    private Executor executorBD;
    private RecyclerView recyclerView;
    private ComicRecyclerViewAdapter adapter;
    private List<Comic> comics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "use on create in Lister");
        setContentView(R.layout.lister);

        //traemos la bbdd y el executor de la clase principal
        db =  ((App) getApplication()).db;
        executorBD = ((App) getApplication()).diskIOExecutor;

        //Establecemos el RecyclerView en la vista
        recyclerView = findViewById(R.id.comic_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Establecemos el ComicRecyclerViewAdapter y usamos el metodo OnComicClick del onBindViewHolder para los clicks
        adapter = new ComicRecyclerViewAdapter(new ComicRecyclerViewAdapter.OnComicClickListener() {
            @Override
            public void onComicClick(Comic comicClicked) {
                moveViewer(comicClicked.getNum());
            }
        });

        //creamos un executor para las consultas a bbdd
        executorBD.execute(new Runnable() {

            @Override
            public void run() {

                //metemos en el ArrayList de comics el listado completo de comics de la BBDD usando un hilo secundario
                comics = FinderBD.findList(db);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //en el hilo principal establecemos en el adaptador ComicRecyclerViewAdapter el ArrayList de comics
                        adapter.updateComics(comics);

                        recyclerView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });
    }

    //usamos este metodo para que en cada click del ComicRecyclerViewAdapter recojamos el numero de comic y nos lo traemos a la vista del Viewer para mostrarlo
    public void moveViewer(int num){
        Intent x = new Intent(this, Viewer.class);
        x.putExtra("num", Integer.toString(num) );
        startActivity(x);

    }

    @Override
    protected void onStart() {                                      //LOG DE INICIO
        super.onStart();
        Log.d("Lifecycle", "use on start in Lister");
    }

    @Override
    protected void onPause() {                                      //LOG DE PAUSA
        super.onPause();
        Log.d("Lifecycle", "use on pause in Lister");
    }

    @Override
    protected void onStop() {                                        //LOG DE PARADA
        super.onStop();
        Log.d("Lifecycle", "use on stop in Lister");
    }

    //En este apartado he reutilizado el codigo de la clase onCreate para que cuando pulsemos el boton atras en la vista Viewer que muestra el comic,
    // se actualice el listado de comics si hemos pulsado anterior o siguiente y despues vuelto atras al listado
    @Override
    protected void onResume() {                                         //LOG DE inicio de actividad
        super.onResume();

        adapter = new ComicRecyclerViewAdapter(new ComicRecyclerViewAdapter.OnComicClickListener() {
            @Override
            public void onComicClick(Comic comicClicked) {
                moveViewer(comicClicked.getNum());
            }
        });

        executorBD.execute(new Runnable() {

            @Override
            public void run() {

                comics = FinderBD.findList(db);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateComics(comics);

                        recyclerView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });


        Log.d("Lifecycle", "use on resume in Lister");
    }

    @Override
    protected void onDestroy() {                                    //LOG DE destruccion de activity
        super.onDestroy();
        Log.d("Lifecycle", "use on destroy in Lister");
    }


}
