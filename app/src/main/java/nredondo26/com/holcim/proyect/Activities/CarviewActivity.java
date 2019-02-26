package nredondo26.com.holcim.proyect.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import nredondo26.com.holcim.R;

public class CarviewActivity extends AppCompatActivity {

    Handler handler;
    private ImageView imagen;
    private Animation rotacion;
    int rid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carview);

        rid=getIntent().getExtras().getInt("id");

        imagen = findViewById(R.id.bello);

        if(rid==1){ imagen.setImageResource(R.drawable.bellog); }
        if(rid==2){ imagen.setImageResource(R.drawable.chiag); }
        if(rid==3){ imagen.setImageResource(R.drawable.palmirag); }
        if(rid==4){ imagen.setImageResource(R.drawable.floridag); }
        if(rid==5){ imagen.setImageResource(R.drawable.nobsag); }
        if(rid==6){ imagen.setImageResource(R.drawable.villavicenciog); }

        rotacion = AnimationUtils.loadAnimation(CarviewActivity.this, R.anim.animacion);
        imagen.startAnimation(rotacion);
        handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(CarviewActivity.this, ContenedoresActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }, 2100);

    }
}



