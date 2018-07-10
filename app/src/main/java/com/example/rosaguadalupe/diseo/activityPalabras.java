package com.example.rosaguadalupe.diseo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class activityPalabras extends AppCompatActivity {
    GridView contenedorLetras;
    TextView Campo,PalabraAEscribir;
    String textoPresionado;

    String[] Palabras =new String[]{"Nieve", "Tienda", "Cartel", "Negro","Idioma","Nariz","Planta"};
    ArrayList<String> letraPorLetra = new ArrayList<String>();
    Calendar calendar;
    SimpleDateFormat mdformat;
    String strDate;
    AudioPlay MusicaPrincipal;
    int id=1;
    int idPalabraRandom,puntaje;

    boolean bloqueo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palabras);
        contenedorLetras=(GridView)findViewById(R.id.contenedorLetras);
        Campo = (TextView)findViewById(R.id.campoPalabras);
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

        idPalabraRandom=selectPalabraAEscribir();
        calendar = Calendar.getInstance();
        mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        strDate =  mdformat.format(calendar.getTime());
        PalabraALetras(idPalabraRandom);

        contenedorLetras.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,letraPorLetra){
            public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView EstiloText = (TextView) view;
                    EstiloText.setTextColor(Color.BLACK);
                    EstiloText.setText(letraPorLetra.get(position));
                    EstiloText.setGravity(Gravity.CENTER);
                    EstiloText.setTextSize(24);
                return EstiloText;
            }
        });
        contenedorLetras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                textoPresionado = adapterView.getItemAtPosition(i).toString();
                Campo.setText(Campo.getText().toString()+ textoPresionado);
              palabraTerminada();
            }
        });

    }
    private int selectPalabraAEscribir(){
        PalabraAEscribir =(TextView)findViewById(R.id.PalabraAescribir);
        Random r = new Random();
        int palabraRandom = r.nextInt(Palabras.length-0);
        PalabraAEscribir.setText(Palabras[palabraRandom]);
        return palabraRandom;
    }
    private void PalabraALetras(int posicion){
        String[] PalabraXpalabra = new String[Palabras[posicion].length()];
        String[] LetrasRandom;
        int index;
        String temp;
        for (int x=0 ;x < Palabras[posicion].length();x++) {
            PalabraXpalabra[x] = Palabras[posicion].substring(x, x + 1);
        }
        Random random = new Random();
        for (int i = PalabraXpalabra.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = PalabraXpalabra[index];
            PalabraXpalabra[index] = PalabraXpalabra[i];
            PalabraXpalabra[i] = temp;
        }


        for (int x=0 ;x < PalabraXpalabra.length;x++)
        letraPorLetra.add(PalabraXpalabra[x]);
    }

    private void palabraTerminada(){
        if(Campo.length() == PalabraAEscribir.length()){

            if(Campo.getText().toString().equals(PalabraAEscribir.getText().toString())){
                puntaje+=5;
                dbConexion conexion = new dbConexion(this,"dbDisxapp",null,1);
                SQLiteDatabase bd = conexion.getWritableDatabase();
                ContentValues newPuntuacion = new ContentValues();
                Cursor puntos;
                newPuntuacion.put("fecha",strDate);
                newPuntuacion.put("puntos",puntaje);
                puntos = bd.rawQuery("SELECT  * FROM puntajePractica", null);
                if(puntos.getCount() > 0){
                    puntos.moveToLast();
                    if(strDate.equals(puntos.getString(1))){
                        ContentValues updatePuntuacion = new ContentValues();
                        updatePuntuacion.put("puntos",puntos.getInt(2)+puntaje);
                        bd.update("puntajePractica",updatePuntuacion,"id =" + puntos.getInt(0),null);

                    }
                    else{
                        bd.insert("puntajePractica", null, newPuntuacion);
                    }
                }
                else{
                    bd.insert("puntajePractica", null, newPuntuacion);
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

                Intent Activity = new Intent( this,activityPuntajeActualHombre.class);
                startActivity(Activity);
            }
            else{
                Campo.setText("");
                Toast.makeText(this, "Intentalo de nuevo! n.n", Toast.LENGTH_SHORT).show();
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
              Intent  Activity = new Intent( this,activityBloqueo.class);
                startActivity(Activity);
            }else{
                MusicaPrincipal.stopAudio();
               Intent Activity = new Intent( this,MainActivity.class);
                startActivity(Activity);
            }
        }else{
            MusicaPrincipal.stopAudio();
           Intent Activity = new Intent( this,MainActivity.class);
            startActivity(Activity);
        }
    }
}
