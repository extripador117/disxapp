package com.example.rosaguadalupe.diseo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class activitySabiasQue extends AppCompatActivity {
    boolean bloqueo;
    Intent Activity;
    AudioPlay MusicaPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabias_que);
        dbConexion mod = new dbConexion(this, "dbDisxapp", null, 1);
        SQLiteDatabase db = mod.getWritableDatabase();
        Cursor puntos = db.rawQuery("SELECT  * FROM bloqueo", null);
        if(puntos.getCount() > 0){
            puntos.moveToLast();
            bloqueo = puntos.getInt(1)!=0;
            if(bloqueo){
                Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }
        }
    }
    @Override
    public void onBackPressed(){
        dbConexion mod = new dbConexion(this, "dbDisxapp", null, 1);
        SQLiteDatabase db = mod.getWritableDatabase();
        Cursor puntos = db.rawQuery("SELECT  * FROM bloqueo", null);
        if(puntos.getCount() > 0){
            puntos.moveToLast();
            bloqueo = puntos.getInt(1)!=0;
            if(bloqueo){
                MusicaPrincipal.stopAudio();
                Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }else{
                 MusicaPrincipal.stopAudio();

                Activity = new Intent( this,MainActivity.class);
                startActivity(Activity);
            }
        }else{
            MusicaPrincipal.stopAudio();

            Activity = new Intent( this,MainActivity.class);
            startActivity(Activity);
        }
    }
}
