package com.example.rosaguadalupe.diseo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activityPuntajeActualHombre extends AppCompatActivity implements View.OnClickListener{
    TextView puntajeHombre,continuarPuntaje;
    AudioPlay  musicaDePuntaje;
    Intent Activity;
    boolean bloqueo;
    Cursor puntos;
    dbConexion mod;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje_actual_hombre);

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


        puntajeHombre = (TextView) findViewById(R.id.puntajeHombre);
        continuarPuntaje = (TextView)findViewById(R.id.continuarPuntaje);
        continuarPuntaje.setOnClickListener(this);
        db = mod.getWritableDatabase();
        puntos = db.rawQuery("SELECT  puntos FROM puntajePractica", null);

        musicaDePuntaje.stopAudio();
        musicaDePuntaje.playAudio(getApplicationContext(),R.raw.chiptronical);
        if (puntos.moveToLast()) {
            puntajeHombre.setText(puntos.getString(0));
        }
        db.close();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.continuarPuntaje:
                Activity = new Intent(view.getContext(), activityBloqueo.class);
                startActivity(Activity);
                break;
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
                musicaDePuntaje.stopAudio();
                Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }else{
                musicaDePuntaje.stopAudio();
                Activity = new Intent( this,MainActivity.class);
                startActivity(Activity);
            }
        }else{
            musicaDePuntaje.stopAudio();
            Activity = new Intent( this,MainActivity.class);
            startActivity(Activity);
        }
    }
}
