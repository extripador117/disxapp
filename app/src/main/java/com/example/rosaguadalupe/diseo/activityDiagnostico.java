package com.example.rosaguadalupe.diseo;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class activityDiagnostico extends AppCompatActivity implements View.OnClickListener{
    TableLayout tabla;
    TableRow columna;
    TextView fechaDatos;
    TextView puntajeDatos ,regresarDiagnostico;
    boolean bloqueo;
    dbConexion mod;
    SQLiteDatabase db;
    Cursor puntos;
    AudioPlay musicaPrincipal;
    Intent Activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico);
        tabla = (TableLayout) findViewById(R.id.tablaDiagnostico);
         mod = new dbConexion(this, "dbDisxapp", null, 1);
         db = mod.getWritableDatabase();
        puntos = db.rawQuery("SELECT  * FROM bloqueo", null);
        if(puntos.getCount() > 0){
            puntos.moveToLast();
            bloqueo = puntos.getInt(1)!=0;
            if(bloqueo){
              Intent  Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }
        }
        regresarDiagnostico=(TextView)findViewById(R.id.regresarDiagnostico);
        regresarDiagnostico.setOnClickListener(this);
         mod = new dbConexion(this, "dbDisxapp", null, 1);
        SQLiteDatabase db = mod.getWritableDatabase();
         puntos = db.rawQuery("SELECT  puntos,fecha FROM puntajeGeneral", null);
        while (puntos.moveToNext()){
            columna = new TableRow(this);
            columna.setId(View.generateViewId());
            columna.setBackgroundColor(Color.WHITE);
            columna.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));

            fechaDatos   = new TextView(this);
            fechaDatos.setId(View.generateViewId());
            fechaDatos.setTextColor(Color.BLACK);
            fechaDatos.setTextSize(19);
            fechaDatos.setGravity(Gravity.CENTER_HORIZONTAL);
            fechaDatos.setText(puntos.getString(1));//aqui
            fechaDatos.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            columna.addView(fechaDatos);

            puntajeDatos= new TextView(this);
            puntajeDatos.setId(View.generateViewId());
            puntajeDatos.setTextColor(Color.BLACK);
            puntajeDatos.setTextSize(19);
            puntajeDatos.setGravity(Gravity.CENTER_HORIZONTAL);
            puntajeDatos.setText(puntos.getString(0));//aqui
            puntajeDatos.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            columna.addView(puntajeDatos);
            tabla.addView(columna, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
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

               Intent Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }else{

                Intent Activity = new Intent( this,MainActivity.class);
                startActivity(Activity);
            }
        }else{

            Intent Activity = new Intent( this,MainActivity.class);
            startActivity(Activity);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.regresarDiagnostico:
                musicaPrincipal.stopAudio();
                Activity = new Intent(view.getContext(), MainActivity.class);
                startActivity(Activity);
                break;
        }
    }

}
