package com.example.rosaguadalupe.diseo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class activityOrtografia extends AppCompatActivity  implements View.OnClickListener{
    Button buttonPalabra1,buttonPalabra2;
    String[] PalabraCorrecta=new String[]{"Bravo", "banda","vaca","vuelo","cuaderno"};
    String[] PalabraIncorrecta = new String[]{"Brado", "vanda","baca","buelo","cuaberno"};
    int PalabrasRandom,puntaje;
    Calendar calendar;
    SimpleDateFormat mdformat;
    String strDate;
    boolean bloqueo;
    AudioPlay musicaPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ortografia);

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


        buttonPalabra1=(Button)findViewById(R.id.buttonPalabra1);
        buttonPalabra2=(Button)findViewById(R.id.buttonPalabra2);
        palabrasRandom();
        calendar = Calendar.getInstance();
        mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        strDate =  mdformat.format(calendar.getTime());


    }

    private void palabrasRandom(){
        Random r = new Random();
         PalabrasRandom = r.nextInt(PalabraCorrecta.length-0);
        int buttonsRandom = r.nextInt(2-0);
        if( buttonsRandom == 1){
            buttonPalabra1.setText(PalabraCorrecta[PalabrasRandom]);
            buttonPalabra2.setText(PalabraIncorrecta[PalabrasRandom]);
        }
        else{
            buttonPalabra2.setText(PalabraCorrecta[PalabrasRandom]);
            buttonPalabra1.setText(PalabraIncorrecta[PalabrasRandom]);
        }

    }

    @Override
    public void onClick(View view) {
            Button b =(Button)view;
            if(b.getText().toString().equals(PalabraCorrecta[PalabrasRandom])){
                puntaje+=5;
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
                    }else{
                        bd.insert("puntajeLectura", null, newPuntuacion);
                    }
                }
                else{
                    bd.insert("puntajeLectura", null, newPuntuacion);
                }
                Intent Activity = new Intent( this,activityPuntajeActualHombre.class);
                startActivity(Activity);
            }
            else{
                Toast.makeText(this, "Intentalo de nuevo! n.n", Toast.LENGTH_SHORT).show();

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
                musicaPrincipal.stopAudio();
                Intent Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }else{
                musicaPrincipal.stopAudio();
                Intent Activity = new Intent( this,MainActivity.class);
                startActivity(Activity);
            }
        }else{
            musicaPrincipal.stopAudio();
            Intent Activity = new Intent( this,MainActivity.class);
            startActivity(Activity);
        }
    }
}
