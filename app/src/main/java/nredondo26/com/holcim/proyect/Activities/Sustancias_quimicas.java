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
import nredondo26.com.holcim.proyect.Activities.Procedimientos.quimica.quimicocero;
import nredondo26.com.holcim.proyect.Adapter.ListAdapter;

public class Sustancias_quimicas extends AppCompatActivity {

    ListView listaleve;
    int idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_squimicas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        idd=getIntent().getExtras().getInt("id");
        Log.e("idquimicos", String.valueOf(idd));


        listaleve= findViewById(R.id.listview);

        // creamos nuestra coleccion de datos
        ArrayList mensajes = new ArrayList();
        mensajes.add("Riesgo Químico");

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
                        intent = new Intent(getApplicationContext(),quimicocero.class);
                        break;

                }
                assert intent != null;
                intent.putExtra("id",idd);
                startActivity(intent);
            }
        });


    }
}


