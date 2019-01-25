package nredondo26.com.holcim.proyect.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import nredondo26.com.holcim.R;


public class MainActivity extends AppCompatActivity {
    Button entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (comprovarpreferencias()) {
            cargarpreferencias();
        }
        entrar = findViewById(R.id.entrar);
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(ent);
                finish();
            }
        });
    }

    private boolean comprovarpreferencias() {
        SharedPreferences preferences = getSharedPreferences("CREDENCIALES", Context.MODE_PRIVATE);
        int id = preferences.getInt("id",0);
        if (id==0) {
            return false;
        } else {
            return true;
        }
    }

    private void cargarpreferencias() {
        SharedPreferences preferences = getSharedPreferences("CREDENCIALES", Context.MODE_PRIVATE);
        int id= preferences.getInt("id", 0);
        Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }


}
