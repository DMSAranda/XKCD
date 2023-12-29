package com.example.tarea3.database;

import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.AbstractCursor;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//clase para la base de datos SQLite
public class ComicDatabase {

    //creamos un helper
    private final ComicDatabaseSQLiteOpenHelper helper;

    //metodo constructor cpasando un contexto
    public ComicDatabase(Context context){

        helper = new ComicDatabaseSQLiteOpenHelper(context, "comic_db", null, 1);
    }

    //metodo para crear un comic dentro de la base de datos
    public void createComic(Comic comic){

        //se abre la base de datos y se usa con el helper una llamada de escritura
        SQLiteDatabase db = helper.getWritableDatabase();
        //establecemos un objeto ContentValues con los valores a escribir
        ContentValues values = new ContentValues();

        //se rellenan con clave-valor los valores a escribir en el objeto ContentValues que recibimos del objeto Comic
        values.put("num", comic.getNum());
        values.put("day", comic.getDay());
        values.put("month", comic.getMonth());
        values.put("year", comic.getYear());
        values.put("title", comic.getTitle());
        values.put("img", comic.getImg());

        //se inserta en la tabla comic un objeto comic con los valores escritos
        db.insert("comic", null, values);
        //cerramos la base de datos
        db.close();
    }

    //metodo para leer un comic dentro de la base de datos
    public Comic readComic(int num){

        //se abre la base de datos y se usa con el helper una llamada de lectura
        SQLiteDatabase db = helper.getReadableDatabase();
        //cremoa un array de string con el valor del numero de comic
        String[] args = {String.valueOf(num)};
        //creamos un cursor que  se va a rellenar con una busqueda SQL que estrae fecha, titulo y url del comic que coincide con el numero de comic recibido por el metodo
        Cursor cursor = db.rawQuery("select day, month, year, title, img from comic where num = ?", args);
        //el comic se instancia nulo
        Comic comic = null;
        //si el cursor se mueve rellenamos el comic con los valores estraidos en el cursor del comic filtrado en la consulta SQL
            if(cursor.moveToFirst()){
                comic = new Comic(num, cursor.getString(0), cursor.getString(1),  cursor.getString(2),  cursor.getString(3), cursor.getString(4) );
            }
        //cerramos la base de datos
        db.close();
        //devolvemos el comic, con datos o vacio segun si existe o no en la base de datos
        return comic;

    }

    //metodo para devolver un ArrayList con todos los comics dentro de la base de datos
    public List<Comic> readList(){

        //se abre la base de datos y se usa con el helper una llamada de lectura
        SQLiteDatabase db = helper.getReadableDatabase();
        //creamos un cursor que se va a rellenar con el resultado de una consulta SQL que estrae fecha, titulo de todos los comics de la BD
        Cursor cursor = db.rawQuery( "select num, day, month, year, title from comic", null);
        //creamos un obeto comic
        Comic comic;
        //creamos un ArrayList de comics
        List<Comic> comics = new ArrayList<>();
        //recorremos el cursor que contienen todos los datos de los comics y usamos el objeto comic que va a ir llenando el ArrayList con todos objetos de tipo comic extraidos en la consulta
        while(cursor.moveToNext()){
            comic = new Comic(cursor.getInt(0), cursor.getString(1),  cursor.getString(2),  cursor.getString(3), cursor.getString(4) );
            comics.add(comic);
        }
        //cerramos base de datos
        db.close();
        //devolvemos el ArrayList
        return comics;

    }



}
