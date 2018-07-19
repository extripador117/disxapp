package com.example.rosaguadalupe.diseo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class activityBloqueo extends AppCompatActivity implements View.OnClickListener{
    String[] Pregunta = new String[]{"Como se llama la aplicacion?","De que color es el cielo?"};
    String[] Password = new String[]{"Dixapp" ,"azul"};
    TextView preguntaBloqueo;
    EditText Respuesta;
    AudioPlay musicaDeJuego;
    int palabraRandom;
    Intent Acitvity;
    boolean bloqueo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo);

        preguntaBloqueo = (TextView)findViewById(R.id.preguntaBloqueo);
        musicaDeJuego.stopAudio();
        musicaDeJuego.playAudio(getApplicationContext(),R.raw.chiptronical);
        Random r = new Random();
        palabraRandom = r.nextInt(Pregunta.length-0);
        preguntaBloqueo.setText(Pregunta[palabraRandom]);
        dbConexion conexion = new dbConexion(this,"dbDisxapp",null,1);
        SQLiteDatabase bd = conexion.getWritableDatabase();
        ContentValues estadoBloqueo = new ContentValues();
        Cursor puntos;

        puntos = bd.rawQuery("SELECT  * FROM bloqueo", null);
        if(puntos.getCount() > 0){
            puntos.moveToLast();
            estadoBloqueo.put("status",1);
            bd.update("bloqueo",estadoBloqueo,"id =" + puntos.getInt(0),null);
        }else{
            estadoBloqueo.put("status",1);
            bd.insert("bloqueo", null, estadoBloqueo);
        }

    }

    @Override
    public void onClick(View view) {
        Respuesta= (EditText)findViewById(R.id.respuestaBloqueo);


        if(Respuesta.getText().toString().equals(Password[palabraRandom]))
        {

            dbConexion conexion = new dbConexion(this,"dbDisxapp",null,1);
            SQLiteDatabase bd = conexion.getWritableDatabase();
            ContentValues estadoBloqueo = new ContentValues();
            Cursor puntos;
            puntos = bd.rawQuery("SELECT * FROM bloqueo", null);
            puntos.moveToLast();
            estadoBloqueo.put("status",0);
            bd.update("bloqueo",estadoBloqueo,"id =" + puntos.getInt(0),null);
            musicaDeJuego.stopAudio();
            Intent Activity = new Intent( this,activityMenuJuegos.class);
            startActivity(Activity);
        }else{
            Respuesta.setText("");
            Toast.makeText(this, "Intetalo otra vez!", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed(){

    }
}
