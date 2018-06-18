package com.example.rosaguadalupe.diseo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class activityDiagnostico extends AppCompatActivity {
    TableLayout tabla;
    TableRow columna;
    TextView fechaDatos;
    TextView puntajeDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico);





        tabla = (TableLayout) findViewById(R.id.tablaDiagnostico);
         columna = new TableRow(this);
        columna.setId(View.generateViewId());
        columna.setBackgroundColor(Color.WHITE);
        columna.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,1f));

        fechaDatos   = new TextView(this);
        fechaDatos.setId(View.generateViewId());
        fechaDatos.setText("12/06/2018");
        fechaDatos.setTextColor(Color.BLACK);
        fechaDatos.setTextSize(19);
        fechaDatos.setGravity(Gravity.CENTER_HORIZONTAL);
        fechaDatos.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        columna.addView(fechaDatos);

        puntajeDatos= new TextView(this);
        puntajeDatos.setId(View.generateViewId());
        puntajeDatos.setText("15");
        puntajeDatos.setTextColor(Color.BLACK);
        puntajeDatos.setTextSize(19);
        puntajeDatos.setGravity(Gravity.CENTER_HORIZONTAL);
        puntajeDatos.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        columna.addView(puntajeDatos);

        tabla.addView(columna, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
    }


}
