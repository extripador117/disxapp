package com.example.rosaguadalupe.diseo;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView juegos;
    ImageView puntaje;
    ImageView Diagnostico;
    ImageView sabiasQue;
    Intent Activity;
    AudioPlay musicaPrincipal;
    boolean bloqueo;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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



        puntaje=(ImageView)findViewById(R.id.puntaje);
        puntaje.setOnClickListener(this);

        juegos=(ImageView)findViewById(R.id.juegos);
        juegos.setOnClickListener(this);

        Diagnostico=(ImageView)findViewById(R.id.diagnostico);
        Diagnostico.setOnClickListener(this);

        sabiasQue=(ImageView)findViewById(R.id.sabiasQue);
        sabiasQue.setOnClickListener(this);

        musicaPrincipal.playAudio(this,R.raw.mainsong);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.juegos:
                Activity = new Intent( view.getContext(),activityMenuJuegos.class);
                startActivity(Activity);
                musicaPrincipal.stopAudio();
                break;

            case R.id.puntaje:
                Activity = new Intent( view.getContext(),activityPuntaje.class);
                startActivity(Activity);
                break;

            case R.id.sabiasQue:
                Activity = new Intent( view.getContext(),activitySabiasQue.class);
                startActivity(Activity);
                break;

            case R.id.diagnostico:
                Activity = new Intent(view.getContext(),activityDiagnostico.class);
                startActivity(Activity);
                break;

            default:
                break;
        }
    }





}

