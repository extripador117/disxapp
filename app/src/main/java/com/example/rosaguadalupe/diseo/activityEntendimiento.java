package com.example.rosaguadalupe.diseo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class activityEntendimiento extends AppCompatActivity  implements View.OnClickListener{
    TextView Pregunta,Oracion;
    Button  VerificarRespuesta;
    EditText Respuesta;
    String[] ArrayOracion =new String[]{"El animal mas grande es el leon", "Hace 5 años tenia 10 años","Pedro tiene un gato de color negro","Víctor comió pastel en el cumpleaños de su amigo Carlos","Diego escribe un poema en su cuaderno amarillo"};
    String[] ArrayPregunta = new String[]{"¿Cual es el animal mas fuerte?","¿Cuantos años tengo ahora?","De que color es el gato  de Pedro?","Que comió Víctor en el cumpleaños de su amigo Carlos?","Que escribe Diego?"};
    String[] ArrayRespuestas = new String[]{"leon","15","negro","pastel" ,"poema"};
    Calendar calendar;
    SimpleDateFormat mdformat;
    String strDate;
    int idRespuesta,puntaje;
    int  vecesJugadas=1;
    boolean bloqueo;
    AudioPlay musicaPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entendimiento);
        dbConexion mod = new dbConexion(this, "dbDisxapp", null, 1);
        SQLiteDatabase db = mod.getWritableDatabase();
        Cursor puntos = db.rawQuery("SELECT  * FROM bloqueo", null);
        if(puntos.getCount() > 0){
            puntos.moveToLast();
            bloqueo = puntos.getInt(1)!=0;
            if(bloqueo){
               Intent Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }
        }

        Pregunta = (TextView)findViewById(R.id.Pregunta);
        Oracion = (TextView)findViewById(R.id.oracion);
        Respuesta =(EditText)findViewById(R.id.Respuesta);
        VerificarRespuesta = (Button) findViewById(R.id.VerificarRespuesta);
        selectOracionRandom();
        calendar = Calendar.getInstance();
        mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        strDate =  mdformat.format(calendar.getTime());


    }

   void selectOracionRandom(){
        Random r = new Random();
        idRespuesta = r.nextInt(ArrayOracion.length-0);
        Oracion.setText(ArrayOracion[idRespuesta]);
        Pregunta.setText(ArrayPregunta[idRespuesta]);
    }

    @Override
    public void onClick(View view) {
        if(Respuesta.getText().length() == ArrayRespuestas[idRespuesta].length()){
            if(Respuesta.getText().toString().equals(ArrayRespuestas[idRespuesta])){
                puntaje=5;
                dbConexion conexion = new dbConexion(this,"dbDisxapp",null,1);
                SQLiteDatabase bd = conexion.getWritableDatabase();
                ContentValues newPuntuacion = new ContentValues();
                Cursor puntos;
                newPuntuacion.put("fecha",strDate);
                newPuntuacion.put("puntos",puntaje);
                puntos = bd.rawQuery("SELECT  * FROM puntajeLectura", null);
                if(puntos.getCount() > 0){
                    puntos.moveToLast();
                    if(strDate.equals(puntos.getString(1))){
                        ContentValues updatePuntuacion = new ContentValues();
                        updatePuntuacion.put("puntos",puntos.getInt(2)+puntaje);
                        bd.update("puntajeLectura",updatePuntuacion,"id =" + puntos.getInt(0),null);
                        vecesJugadas+=1;
                        Respuesta.setText("");
                        selectOracionRandom();
                        Toast.makeText(this, "Muy bien! n.n", Toast.LENGTH_SHORT).show();
                    }else{
                        bd.insert("puntajeLectura", null, newPuntuacion);
                    }
                }
                else{
                    bd.insert("puntajeLectura", null, newPuntuacion);
                }

                SQLiteDatabase bd2 = conexion.getWritableDatabase();
                puntos = bd2.rawQuery("SELECT  * FROM puntajeGeneral", null);
                if(puntos.getCount() > 0){
                    puntos.moveToLast();
                    if(strDate.equals(puntos.getString(1))){
                        ContentValues updatePuntuacion = new ContentValues();
                        updatePuntuacion.put("puntos",puntos.getInt(2)+puntaje);
                        bd.update("puntajeGeneral",updatePuntuacion,"id =" + puntos.getInt(0),null);
                    }
                    else{
                        bd.insert("puntajeGeneral", null, newPuntuacion);
                    }
                }
                else{
                    bd.insert("puntajeGeneral", null, newPuntuacion);
                }
            }
            else{
                vecesJugadas+=1;
                Respuesta.setText("");
                selectOracionRandom();
                Toast.makeText(this, "Intentalo de nuevo! n.n", Toast.LENGTH_SHORT).show();

            }
        } else{
            vecesJugadas+=1;
            Respuesta.setText("");
            selectOracionRandom();
            Toast.makeText(this, "Intentalo de nuevo! n.n", Toast.LENGTH_SHORT).show();

        }
        if (vecesJugadas == 5){
            Intent Activity = new Intent( this,activityPuntajeActualMujer.class);
            startActivity(Activity);
        }
    }


}
