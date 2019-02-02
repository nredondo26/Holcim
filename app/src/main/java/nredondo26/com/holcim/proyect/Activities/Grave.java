package nredondo26.com.holcim.proyect.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.grave.*;
import nredondo26.com.holcim.proyect.Adapter.ListAdapter;

public class Grave extends AppCompatActivity {

    ListView listaleve;
    int idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grave);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        idd=getIntent().getExtras().getInt("id");

        listaleve= findViewById(R.id.listview);

        // creamos nuestra coleccion de datos
        ArrayList mensajes = new ArrayList();
        mensajes.add("Paro Cardio-respiratorio(RCP)");
        mensajes.add("Perdida del Estado de Conciencia");
        mensajes.add("Electrocución");
        mensajes.add("Asfixia / Atragantamiento");
        mensajes.add("Trauma Cráneo-encefálico");
        mensajes.add("Trauma Torácico");
        mensajes.add("Trauma de Columna");
        mensajes.add("Trauma Térmico");
        mensajes.add("Heridas y Fracturas");
        mensajes.add("Heridas y Fracturas");

        // creamos el listado
        ListAdapter listAdapter = new ListAdapter(this, mensajes);

        // establecemos el adaptador en la lista
        listaleve.setAdapter(listAdapter);

        listaleve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = null;
                switch(position){
                    case 0:
                        intent = new Intent(getApplicationContext(),gravecero.class);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(),graveuno.class);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(),gravedos.class);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(),gravetres.class);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(),gravecuatro.class);
                        break;
                    case 5:
                        intent = new Intent(getApplicationContext(),gravecinco.class);
                        break;
                    case 6:
                        intent = new Intent(getApplicationContext(),graveseis.class);
                        break;
                    case 7:
                        intent = new Intent(getApplicationContext(),gravesiete.class);
                        break;
                    case 8:
                        intent = new Intent(getApplicationContext(),graveocho.class);
                        break;
                    case 9:
                        intent = new Intent(getApplicationContext(),gravenueve.class);
                        break;
                }
                assert intent != null;
                intent.putExtra("id",idd);
                startActivity(intent);
            }
        });


    }
}
