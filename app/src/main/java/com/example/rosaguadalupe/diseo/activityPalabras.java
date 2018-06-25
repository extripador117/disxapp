package com.example.rosaguadalupe.diseo;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.Random;

public class activityPalabras extends AppCompatActivity {
    GridView contenedorLetras;
    TextView Campo,PalabraAEscribir;
    String textoPresionado;

    String[] Palabras =new String[]{"Perro", "Disco", "Pecera", "Camioneta"};
    ArrayList<String> letraPorLetra = new ArrayList<String>();
    int id=1;
    int idPalabraRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palabras);
        contenedorLetras=(GridView)findViewById(R.id.contenedorLetras);
        Campo = (TextView)findViewById(R.id.campoPalabras);

        idPalabraRandom=selectPalabraAEscribir();
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

        for (int x=0 ;x < Palabras[posicion].length();x++)
        letraPorLetra.add(Palabras[posicion].substring(x,x+1));
    }

    private void palabraTerminada(){
        if(Campo.length() == PalabraAEscribir.length()){

            if(Campo.getText().toString().equals(PalabraAEscribir.getText().toString())){
                Intent Activity = new Intent( this,activityPuntajeActualHombre.class);
                startActivity(Activity);
            }
            else{
                Campo.setText("");
                Toast.makeText(this, "Intentalo de nuevo! n.n", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
