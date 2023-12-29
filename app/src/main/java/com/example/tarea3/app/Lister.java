package com.example.tarea3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.tarea3.R;

public class Lister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "use on create in Lister");
        setContentView(R.layout.lister);
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

    @Override
    protected void onResume() {                                         //LOG DE inicio de actividad
        super.onResume();
        Log.d("Lifecycle", "use on resume in Lister");
    }

    @Override
    protected void onDestroy() {                                    //LOG DE destruccion de activity
        super.onDestroy();
        Log.d("Lifecycle", "use on destroy in Lister");
    }
}