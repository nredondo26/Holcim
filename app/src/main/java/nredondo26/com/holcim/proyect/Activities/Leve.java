package nredondo26.com.holcim.proyect.Activities;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.leve.levecero;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.leve.levecuatro;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.leve.levedos;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.leve.levetres;
import nredondo26.com.holcim.proyect.Activities.Procedimientos.leve.leveuno;
import nredondo26.com.holcim.proyect.Adapter.ListAdapter;

public class Leve extends AppCompatActivity {

    ListView listaleve;
    int idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leve);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        idd=getIntent().getExtras().getInt("id");

        listaleve = findViewById(R.id.listview);

        final ArrayList<String> mensajes = new ArrayList<String>();
        mensajes.add("Gripe o Catarro");
        mensajes.add("Vértigo, Desmayo-Síncope");
        mensajes.add("Malestar Estomacal");
        mensajes.add("Esguince");
        mensajes.add("Caída sin perdida de conciencia");

        ListAdapter listAdapter = new ListAdapter(this, mensajes);
        listaleve.setAdapter(listAdapter);

        listaleve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = null;
                    switch(position){
                        case 0:
                            intent = new Intent(getApplicationContext(),levecero.class);
                            break;
                        case 1:
                            intent = new Intent(getApplicationContext(),leveuno.class);
                            break;
                        case 2:
                            intent = new Intent(getApplicationContext(),levedos.class);
                            break;
                        case 3:
                            intent = new Intent(getApplicationContext(),levetres.class);
                            break;
                        case 4:
                            intent = new Intent(getApplicationContext(),levecuatro.class);
                            break;
                    }
                assert intent != null;
                intent.putExtra("id",idd);
                startActivity(intent);
            }
        });

    }
}
