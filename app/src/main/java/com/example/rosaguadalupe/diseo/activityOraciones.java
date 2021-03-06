package com.example.rosaguadalupe.diseo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class activityOraciones extends AppCompatActivity implements View.OnClickListener {

    TextView CampoOraciones,oracionAEscribir;
    GridView contenedor;
    ImageView flechaRetrocesoOracion;
    LinearLayout.LayoutParams boton;
    String[] Oraciones =new String[]{"EL leon es grande", "Diego escribe un poema", "Víctor comió pastel","Jef es muy jugueton","mi mama me ama","las pilas tienen mucha energia"};
    String palabraPresionada;
    ArrayList<String> PalabraPorPalabra = new ArrayList<String>();
    int posicionesAborrar=0;
    int idOracionRandom,puntaje;
    int vecesJugadas=1;
    Calendar calendar;
    SimpleDateFormat mdformat;
    String strDate;
    AudioPlay musicaPrincipal;
    boolean bloqueo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oraciones);

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


        contenedor = (GridView) findViewById(R.id.contenedorOraciones);
        CampoOraciones = (TextView)findViewById(R.id.oracion);
        flechaRetrocesoOracion = (ImageView)findViewById(R.id.flechaRetrocesoOracion);
        flechaRetrocesoOracion.setOnClickListener(this);
        calendar = Calendar.getInstance();
        mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        strDate =  mdformat.format(calendar.getTime());
        newGame();

        contenedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                posicionesAborrar+=1;
                palabraPresionada = adapterView.getItemAtPosition(i).toString();
                CampoOraciones.setText(CampoOraciones.getText().toString()+" "+palabraPresionada);
                oracionTerminada();
            }
        });
    }
    public SQLiteDatabase coneccion(){
        dbConexion mod=new dbConexion(this,"dbDisxapp",null,1);
        SQLiteDatabase db=mod.getWritableDatabase();
        return db;
    }
    private int selectOracionRandom(){
        oracionAEscribir = (TextView)findViewById(R.id.oracionAEscribir);
        Random r = new Random();
        int OracionRandom = r.nextInt(Oraciones.length-0);
        oracionAEscribir.setText(Oraciones[OracionRandom]);
        return OracionRandom;
    }
    private void oracionAPalabras(int posicion){
        String[] palabras = Oraciones [posicion].split("\\s+");
        int index;
        String temp;
        Random random = new Random();
        for (int i = palabras.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            temp = palabras[index];
            palabras[index] = palabras[i];
            palabras[i] = temp;
        }
        for (int x=0 ;x < palabras.length;x++)
            PalabraPorPalabra.add(palabras[x]);
    }
    private void oracionTerminada(){
        if( CampoOraciones.length() >= oracionAEscribir.length()){

            int posicionFinal = CampoOraciones.length();
            String result = CampoOraciones.getText().toString().substring(1,posicionFinal);


            if (oracionAEscribir.getText().toString().equals(result)){
                    puntaje=5;
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
                                    vecesJugadas+=1;
                                    CampoOraciones.setText("");
                                     posicionesAborrar=0;
                                    newGame();
                                    Toast.makeText(this, "Muy bien! n.n", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                bd.insert("puntajeGeneral", null, newPuntuacion);
                            }
                    }
                    else{
                        bd.insert("puntajeGeneral", null, newPuntuacion);
                    }

            }else{
                vecesJugadas+=1;
                CampoOraciones.setText("");
                posicionesAborrar=0;
                newGame();

                Toast.makeText(this, "Puedes mejorar n.n!", Toast.LENGTH_SHORT).show();
            }
        }

        if (vecesJugadas == 5){
            Intent Activity = new Intent( this,activityPuntajeActualHombre.class);
            startActivity(Activity);
        }
    }

    public void newGame(){
        PalabraPorPalabra.clear();
        idOracionRandom = selectOracionRandom();
        oracionAPalabras(idOracionRandom);
        contenedor.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,PalabraPorPalabra){
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView EstiloText = (TextView) view;
                EstiloText.setTextColor(0xff6950eb);
                EstiloText.setText(PalabraPorPalabra.get(position));
                EstiloText.setGravity(Gravity.CENTER);
                EstiloText.setTypeface(null, Typeface.BOLD);
                EstiloText.setTextSize(20);
                return EstiloText;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.flechaRetrocesoOracion:
                String palabra=  CampoOraciones.getText().toString();
                String palabraAconcatenar="";
                if(palabra.length()> 0){
                    String[] palabras = CampoOraciones.getText().toString().split("\\s+");

                    for (int x=0;x<posicionesAborrar;x++){
                        palabraAconcatenar += palabras[x]+" ";
                    }
                    CampoOraciones.setText(palabraAconcatenar.substring(0,palabraAconcatenar.length()-1));
                    posicionesAborrar--;
                }
                break;
        }
    }
}
