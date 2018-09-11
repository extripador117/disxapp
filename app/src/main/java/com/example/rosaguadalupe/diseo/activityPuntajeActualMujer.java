package com.example.rosaguadalupe.diseo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activityPuntajeActualMujer extends AppCompatActivity implements View.OnClickListener{
  TextView puntajeMujer,RegresarPuntajeM;
    AudioPlay  musicaDePuntaje;
    Intent Activity;
    boolean bloqueo;
    Cursor puntos;
    dbConexion mod;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_actual_mujer);

        mod = new dbConexion(this, "dbDisxapp", null, 1);
        db = mod.getWritableDatabase();
        puntos = db.rawQuery("SELECT  * FROM bloqueo", null);
        if(puntos.getCount() > 0){
            puntos.moveToLast();
            bloqueo = puntos.getInt(1)!=0;
            if(bloqueo){
                Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }
        }
        puntajeMujer =(TextView)findViewById(R.id.puntajeMujer);
        RegresarPuntajeM=(TextView)findViewById(R.id.RegresarPuntajeM);
        RegresarPuntajeM .setOnClickListener(this);
        db = mod.getWritableDatabase();
        puntos = db.rawQuery("SELECT  puntos FROM puntajeLectura", null);
        musicaDePuntaje.stopAudio();
        musicaDePuntaje.playAudio(getApplicationContext(),R.raw.chiptronical);
        if (puntos.moveToLast()) {
            puntajeMujer.setText(puntos.getString(0));
        }
        db.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.RegresarPuntajeM:
                Activity = new Intent(view.getContext(), activityBloqueo.class);
                startActivity(Activity);
                break;
        }

    }

    @Override
    public void onBackPressed(){

    }

}
