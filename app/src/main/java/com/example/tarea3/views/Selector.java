package com.example.tarea3.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.example.tarea3.R;

//clase para la Activity de seleccion de comic "Selector" cn opcion de mostrar el listado de todos los descargados
public class Selector extends AppCompatActivity {


    //el edittext del numero de comic
    private EditText id;

    //metodo que nos lleva al activity Viewer y envia el numero de comic seleccionado
    public void send(){

        Log.d("click", "I click on comic button");                              //LOG DE CLICK
        Intent x = new Intent(this, Viewer.class);

        x.putExtra("num", id.getText().toString() );                     //enviamos parametros a la activity viewer
        Log.d("value", "valor id: " + id.getText().toString());
        startActivity(x);

    }

    //metodo que nos lleva al Activity Lister
    public void send2(){

        Log.d("click", "I click on lister button");                              //LOG DE CLICK
        Intent x = new Intent(this, Lister.class);

        startActivity(x);

    }


    public boolean idChecker(EditText id) {            //comprobamos que el id este en el rango

       boolean CheckerAll;

        int x = 0;

        boolean Checker3 = false;

        boolean resultado;

            try {                        //con este try-catch evitamos que la app se cierre al borrar y dejar vacio el campo de id
                 x =  Integer.parseInt(id.getText().toString());
                resultado = true;
            } catch (NumberFormatException excepcion) {
                resultado = false;
            }


        if (resultado){

            if (x > 0 && x < 2723){      //comprobado el numero de comics que tiene la web xkcd, forzamos que estemos dentro del rango
                Checker3 = true;
            }
        }

        boolean Checker1 = TextUtils.isDigitsOnly(id.getText());        //solo se pone en true si es un numero

        boolean Checker2 = !(id.getText().toString().isEmpty());        //solo se pone en true si no esta vacio

        CheckerAll = (Checker1 && Checker2 && Checker3 );    //obligamos que se cumplan todas las condiciones

        return CheckerAll;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //capturamos la vista "Selector"
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle", "use on create in Selector");
        setContentView(R.layout.selector);

        id = findViewById(R.id.id);     //capturamos el id del editext

        Button button = findViewById(R.id.comic);          //CAPTURAMOS EL BOTON comic DE LA VISTA
        button.setEnabled(false);                   //BOTON DESHABILITADO POR DEFECTO
        button.setBackgroundColor(Color.GRAY);
        button.setTextColor(Color.parseColor("#ffffff"));
        button.setOnClickListener(view -> {             //FUNCION DE FLECHA PARA EL CLICK

            send();               //LANZANDO INTENT metodo send arriba
        });

        Button button2 = findViewById(R.id.list);          //CAPTURAMOS EL BOTON comic DE LA VISTA
        button2.setOnClickListener(view -> {             //FUNCION DE FLECHA PARA EL CLICK

            send2();               //LANZANDO INTENT metodo sen2 arriba
        });

        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!idChecker(id)) {                  //LANZAMOS ERROR SI no esta dentro del rango
                   id.setError("Must be a number within the range");
                   button.setBackgroundColor(Color.GRAY);
                }
                else{
                   button.setEnabled(idChecker(id));   //cuando se cumpla ponemos el boton habilitado
                   button.setBackgroundColor(Color.BLACK);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    protected void onStart() {                                      //LOG DE INICIO
        super.onStart();
        Log.d("Lifecycle", "use on start in Selector");
    }

    @Override
    protected void onPause() {                                      //LOG DE PAUSA
        super.onPause();
        Log.d("Lifecycle", "use on pause in Selector");
    }

    @Override
    protected void onStop() {                                        //LOG DE PARADA
        super.onStop();
        Log.d("Lifecycle", "use on stop in Selector");
    }

    @Override
    protected void onResume() {                                         //LOG DE inicio de actividad
        super.onResume();
        Log.d("Lifecycle", "use on resume in Selector");
    }

    @Override
    protected void onDestroy() {                                    //LOG DE destruccion de activity
        super.onDestroy();
        Log.d("Lifecycle", "use on destroy in Selectors");
    }
}