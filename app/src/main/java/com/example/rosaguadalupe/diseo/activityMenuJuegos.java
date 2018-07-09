package com.example.rosaguadalupe.diseo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activityMenuJuegos extends AppCompatActivity  implements View.OnClickListener{
    Intent Activity;
    TextView opcionOrtografia,opcionOraciones,opcionPalabras,opcionEntendimiento;
    AudioPlay musicaDeJuego;
    boolean bloqueo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_juegos);
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


        opcionOrtografia =(TextView)findViewById(R.id.opcionOrtografia);
        opcionOrtografia.setOnClickListener(this);


        opcionOraciones =(TextView)findViewById(R.id.opcionOraciones);
        opcionOraciones.setOnClickListener(this);

        opcionPalabras =(TextView)findViewById(R.id.opcionPalabras);
        opcionPalabras.setOnClickListener(this);

        opcionEntendimiento = (TextView)findViewById(R.id.opcionEntendimiento);
        opcionEntendimiento.setOnClickListener(this);


        musicaDeJuego.playAudio(getApplicationContext(),R.raw.musicadejuego);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.opcionOraciones:
                Activity = new Intent( view.getContext(),activityOraciones.class);
                startActivity(Activity);
                break;

            case R.id.opcionOrtografia:
                Activity = new Intent( view.getContext(),activityOrtografia.class);
                startActivity(Activity);
                break;

            case R.id.opcionPalabras:
                Activity = new Intent( view.getContext(),activityPalabras.class);
                startActivity(Activity);
                break;

            case R.id.opcionEntendimiento:
                Activity = new Intent( view.getContext(),activityEntendimiento.class);
                startActivity(Activity);
                break;

            default:
                break;
        }
    }

}
