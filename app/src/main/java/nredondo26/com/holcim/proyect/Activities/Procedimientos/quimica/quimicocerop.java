package nredondo26.com.holcim.proyect.Activities.Procedimientos.quimica;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Activities.LlamarNueveonce;
import nredondo26.com.holcim.proyect.Activities.Traslado;

public class quimicocerop extends AppCompatActivity {

    Button botonpaciente2;
    Button botonprocedimiento2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quimicocerop);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        botonpaciente2= findViewById(R.id.botonpaciente2);
        botonpaciente2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte= new Intent(getApplicationContext(), Traslado.class);
                startActivity(inte);
            }
        });

        botonprocedimiento2= findViewById(R.id.botonprocedimiento2);
        botonprocedimiento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte= new Intent(getApplicationContext(), LlamarNueveonce.class);
                startActivity(inte);
            }
        });
    }
}
