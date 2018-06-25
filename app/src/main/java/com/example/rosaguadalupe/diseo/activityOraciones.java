package com.example.rosaguadalupe.diseo;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class activityOraciones extends AppCompatActivity  {
    TextView CampoOraciones,oracionAEscribir;
    GridView contenedor;
    LinearLayout.LayoutParams boton;
    String[] Oraciones =new String[]{"EL leon es grande", "I love you Itzel", "hola que hace maestra", "hay muchas Camionetas"};
    String palabraPresionada;
    ArrayList<String> PalabraPorPalabra = new ArrayList<String>();
    int idOracionRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oraciones);
        contenedor = (GridView) findViewById(R.id.contenedorOraciones);
        CampoOraciones = (TextView)findViewById(R.id.CampoOraciones);
        idOracionRandom = selectOracionRandom();
        oracionAPalabras(idOracionRandom);

        contenedor.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,PalabraPorPalabra){
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView EstiloText = (TextView) view;
                EstiloText.setTextColor(Color.BLACK);
                EstiloText.setText(PalabraPorPalabra.get(position));
                EstiloText.setGravity(Gravity.CENTER);
                EstiloText.setTextSize(20);
                return EstiloText;
            }
        });
        contenedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                palabraPresionada = adapterView.getItemAtPosition(i).toString();
                CampoOraciones.setText(CampoOraciones.getText().toString()+" "+palabraPresionada);

            }
        });
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
        for (int x=0 ;x < palabras.length;x++)
            PalabraPorPalabra.add(palabras[x]);
    }
}
