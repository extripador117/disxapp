package com.example.rosaguadalupe.diseo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activityPuntaje extends AppCompatActivity implements View.OnClickListener{
    Cursor cursorPuntosPractica,cursorPuntosLectura;
    dbConexion conexion;
    SQLiteDatabase bd;
    TextView PuntosPractica,PuntosLectura,regresarPuntaje;
    int PuntajeTotalLectura,PuntajeTotalPractica;
    boolean bloqueo;
    Intent Activity;
    AudioPlay MusicaPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje);
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


        regresarPuntaje=(TextView)findViewById(R.id.regresarPuntaje);
        regresarPuntaje.setOnClickListener(this);
        PuntosPractica=(TextView)findViewById(R.id.puntosPractica);
        PuntosLectura=(TextView)findViewById(R.id.puntosLectura);
        conexion = new dbConexion(this,"dbDisxapp",null,1);
        bd = conexion.getWritableDatabase();
        cursorPuntosPractica = bd.rawQuery("SELECT  puntos FROM puntajePractica", null);
        cursorPuntosLectura = bd.rawQuery("SELECT puntos FROM  puntajeLectura",null);
        if(cursorPuntosLectura.getCount() > 0){
                cursorPuntosLectura.moveToFirst();

                while(!cursorPuntosLectura.isAfterLast()){
                    PuntajeTotalLectura = cursorPuntosLectura.getInt(0);
                    cursorPuntosLectura.moveToNext();
                }
                PuntosLectura.setText(""+PuntajeTotalLectura+"puntos");
        }
        else{
            PuntosLectura.setText("0");
        }
        if(cursorPuntosPractica.getCount() > 0){
            cursorPuntosPractica.moveToFirst();

            while(!cursorPuntosPractica.isAfterLast()){
                PuntajeTotalPractica +=  cursorPuntosPractica.getInt(0);
                cursorPuntosPractica.moveToNext();
            }
            PuntosPractica.setText(""+PuntajeTotalPractica+" puntos");
        }
        else{
            PuntosPractica.setText("0");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.regresarPuntaje:
                MusicaPrincipal.stopAudio();
                Activity = new Intent(view.getContext(), MainActivity.class);
                startActivity(Activity);
                break;
        }
    }

}
