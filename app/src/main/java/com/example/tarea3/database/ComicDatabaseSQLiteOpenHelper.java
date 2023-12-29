package com.example.tarea3.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//Helper de la Base de datos SQLite
public class ComicDatabaseSQLiteOpenHelper extends SQLiteOpenHelper {


    public ComicDatabaseSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //cuando creamos la base de datos le asignamos a la tabla el titulo comic, usamos el numero de comic como clave primaria del id, y cremao s las columnas, dia, mes, año, tutulo y la url de la imagen
        db.execSQL("create table comic(num integer primary key, day text, month text, year text, title text, img text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
