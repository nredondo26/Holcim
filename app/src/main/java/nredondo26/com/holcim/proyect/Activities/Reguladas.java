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
import nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada.reguladacero;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada.reguladacinco;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada.reguladacuatro;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada.reguladados;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada.reguladaseis;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada.reguladasiete;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada.reguladatres;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada.reguladauno;
import nredondo26.com.holcim.proyect.Adapter.ListAdapter;

public class Reguladas  extends AppCompatActivity {
    ListView listaleve;
    int idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reguladas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        idd=getIntent().getExtras().getInt("id");


        listaleve= findViewById(R.id.listview);

        // creamos nuestra coleccion de datos
        ArrayList mensajes = new ArrayList();
        mensajes.add("Eventos Vascular Cerebral");
        mensajes.add("Síndrome Ortostatico");
        mensajes.add("Dolor Precordial");
        mensajes.add("Estado Mental Alterado");
        mensajes.add("Convulsiones O Epilepsia");
        mensajes.add("Crisis Hipertensiva/Migraña");
        mensajes.add("Sangrado Nasal/Epistaxis");
        mensajes.add("Embrazo de Riesgo");

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
                        intent = new Intent(getApplicationContext(),reguladacero.class);
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(),reguladauno.class);
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(),reguladados.class);
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(),reguladatres.class);
                        break;
                    case 4:
                        intent = new Intent(getApplicationContext(),reguladacuatro.class);
                        break;
                    case 5:
                        intent = new Intent(getApplicationContext(),reguladacinco.class);
                        break;
                    case 6:
                        intent = new Intent(getApplicationContext(),reguladaseis.class);
                        break;
                    case 7:
                        intent = new Intent(getApplicationContext(),reguladasiete.class);
                        break;
                }
                assert intent != null;
                intent.putExtra("id",idd);
                startActivity(intent);
            }
        });


    }
}

