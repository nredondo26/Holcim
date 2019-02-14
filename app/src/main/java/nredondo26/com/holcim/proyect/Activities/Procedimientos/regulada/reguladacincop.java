package nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Activities.Centros;
import nredondo26.com.holcim.proyect.Activities.LlamarNueveonce;
import nredondo26.com.holcim.proyect.Activities.Traslado;

public class reguladacincop extends AppCompatActivity {

    Button botonpaciente2;
    Button botonprocedimiento2;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reguladacincop);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        id=getIntent().getExtras().getInt("id");

        botonpaciente2= findViewById(R.id.botonpaciente2);
        botonpaciente2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte= new Intent(getApplicationContext(), Centros.class);
                inte.putExtra("id", id);
                startActivity(inte);
            }
        });

        botonprocedimiento2= findViewById(R.id.botonprocedimiento2);
        botonprocedimiento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte= new Intent(getApplicationContext(), LlamarNueveonce.class);
                inte.putExtra("id", id);
                startActivity(inte);
            }
        });
    }
}
