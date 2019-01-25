package nredondo26.com.holcim.proyect.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import nredondo26.com.holcim.R;

public class BitacoraActivity extends AppCompatActivity {

    EditText uno,dos,tres,cuatro,cinco,seis,siete,ocho,nueve,diez,once,doce,catorce,trece,diesisiete,diesiocho,diesinueve,veinte,veintiuno,veintidos;
    String auno,ados,atres,acuatro,acinco,aseis,asiete,aocho,anueve,adiez,aonce,adoce,acatorce,atrece,adiesisiete,adiesiocho,adiesinueve,aveinte,aveintiuno,aveintidos;
    Button Registar;
    ProgressDialog progressDialog;
    String HttpUrl = "http://api-holcim.com/registrobitacora.php";
    RequestQueue requestQueue;
    Boolean CheckEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        uno= findViewById(R.id.uno);
        dos= findViewById(R.id.dos);
        dos.setText(formattedDate);
        tres= findViewById(R.id.tres);
        cuatro= findViewById(R.id.cuatro);
        cinco= findViewById(R.id.cinco);
        seis= findViewById(R.id.seis);
        siete= findViewById(R.id.siete);
        ocho= findViewById(R.id.ocho);
        nueve= findViewById(R.id.nueve);
        diez= findViewById(R.id.diez);
        once= findViewById(R.id.once);
        doce= findViewById(R.id.doce);
        catorce= findViewById(R.id.catorce);
        trece= findViewById(R.id.trece);
        diesisiete= findViewById(R.id. diesisiete);
        diesiocho= findViewById(R.id.diesiocho);
        diesinueve= findViewById(R.id.diesinueve);
        veinte= findViewById(R.id.veinte);
        veintiuno= findViewById(R.id.veintiuno);
        veintidos= findViewById(R.id.veintidos);
        Registar= findViewById(R.id.Registar);
        requestQueue = Volley.newRequestQueue(BitacoraActivity.this);
        progressDialog = new ProgressDialog(BitacoraActivity.this);

        Registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    Registarusuario();
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void CheckEditTextIsEmptyOrNot() {

        auno = uno.getText().toString().trim();
        ados = dos.getText().toString().trim();
        atres = tres.getText().toString().trim();
        acuatro = cuatro.getText().toString().trim();
        acinco = cinco.getText().toString().trim();
        aseis = seis.getText().toString().trim();
        asiete = siete.getText().toString().trim();
        aocho = ocho.getText().toString().trim();
        anueve = nueve.getText().toString().trim();
        adiez = diez.getText().toString().trim();
        aonce = once.getText().toString().trim();
        adoce = doce.getText().toString().trim();
        acatorce = catorce.getText().toString().trim();
        atrece = trece.getText().toString().trim();
        adiesisiete = diesisiete.getText().toString().trim();
        adiesiocho = diesiocho.getText().toString().trim();
        adiesinueve = diesinueve.getText().toString().trim();
        aveinte = veinte.getText().toString().trim();
        aveintiuno = veintiuno.getText().toString().trim();
        aveintidos = veintidos.getText().toString().trim();

        CheckEditText = !TextUtils.isEmpty(auno)&&
                        !TextUtils.isEmpty(ados)&&
                        !TextUtils.isEmpty(atres)&&
                        !TextUtils.isEmpty(acuatro)&&
                        !TextUtils.isEmpty(acinco)&&
                        !TextUtils.isEmpty(aseis)&&
                        !TextUtils.isEmpty(asiete)&&
                        !TextUtils.isEmpty(aocho)&&
                        !TextUtils.isEmpty(anueve)&&
                        !TextUtils.isEmpty(adiez)&&
                        !TextUtils.isEmpty(aonce)&&
                        !TextUtils.isEmpty(adoce)&&
                        !TextUtils.isEmpty(acatorce)&&
                        !TextUtils.isEmpty(atrece)&&
                        !TextUtils.isEmpty(adiesisiete)&&
                        !TextUtils.isEmpty(adiesiocho)&&
                        !TextUtils.isEmpty(adiesinueve)&&
                        !TextUtils.isEmpty(aveinte)&&
                        !TextUtils.isEmpty(aveintiuno)&&
                        !TextUtils.isEmpty(aveintidos);
    }

    public void Registarusuario() {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();
                        if (ServerResponse.contains("exitoso")) {
                            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(BitacoraActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Problemas con la conexión", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("auno", auno);
                params.put("ados", ados);
                params.put("atres", atres);
                params.put("acuatro", acuatro);
                params.put("acinco", acinco);
                params.put("aseis", aseis);
                params.put("asiete", asiete);
                params.put("aocho", aocho);
                params.put("anueve", anueve);
                params.put("adiez", adiez);
                params.put("aonce", aonce);
                params.put("adoce", adoce);
                params.put("acatorce", acatorce);
                params.put("atrece", atrece);
                params.put("adiesisiete", adiesisiete);
                params.put("adiesiocho", adiesiocho);
                params.put("adiesinueve", adiesinueve);
                params.put("aveinte", aveinte);
                params.put("aveintiuno", aveintiuno);
                params.put("aveintidos", aveintidos);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BitacoraActivity.this);
        requestQueue.add(stringRequest);
    }
}
