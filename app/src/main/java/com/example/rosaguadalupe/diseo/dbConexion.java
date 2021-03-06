package com.example.rosaguadalupe.diseo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbConexion extends SQLiteOpenHelper {

    public dbConexion(Context cont, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(cont,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS puntajeGeneral(id integer primary key, fecha date, puntos integer)");

        db.execSQL("create table IF NOT EXISTS puntajeLectura(id integer primary key,  fecha date, puntos integer)");

        db.execSQL("create table IF NOT EXISTS puntajePractica(id integer primary key,  fecha date, puntos integer)");

        db.execSQL("create table IF NOT EXISTS bloqueo(id integer primary key , status integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
