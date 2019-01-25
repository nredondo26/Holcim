package nredondo26.com.holcim.proyect.Activities.Procedimientos.regulada;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Activities.Paciente;

public class reguladauno extends AppCompatActivity {
//gripe

    Button paciente;
    Button procedimiento;
    String cod= "301";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reguladauno);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        paciente= findViewById(R.id.botonpaciente);
        procedimiento= findViewById(R.id.botonprocedimiento);

        paciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ent = new Intent(getApplicationContext(), Paciente.class);
                ent.putExtra("cod", cod);
                startActivity(ent);
            }
        });

        procedimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ent = new Intent(getApplicationContext(), reguladaunop.class);
                startActivity(ent);
            }
        });
    }
}
