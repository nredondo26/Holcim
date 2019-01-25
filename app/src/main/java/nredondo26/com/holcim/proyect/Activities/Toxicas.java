package nredondo26.com.holcim.proyect.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import nredondo26.com.holcim.R;

import nredondo26.com.holcim.proyect.Activities.Procedimientos.toxica.toxicacero;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.toxica.toxicados;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.toxica.toxicauno;
import nredondo26.com.holcim.proyect.Adapter.ListAdapter;

public class Toxicas  extends AppCompatActivity {
    ListView listaleve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toxicas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listaleve= findViewById(R.id.listview);

        // creamos nuestra coleccion de datos
        ArrayList mensajes = new ArrayList();
        mensajes.add("Congestión Alcohólica/ Etilismo");
        mensajes.add("Efectos por uso de Drogas");
        mensajes.add("Envenenamiento por Insectos");

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
                        intent = new Intent(getApplicationContext(),toxicacero.class);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(),toxicauno.class);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(),toxicados.class);
                        break;

                }
                startActivity(intent);
            }
        });


    }
}
