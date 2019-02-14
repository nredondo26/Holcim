package nredondo26.com.holcim.proyect.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import nredondo26.com.holcim.R;

public class EditarActivity extends AppCompatActivity {

    int rrid;
    EditText editnombre, editapellidos, editemail, editpass , zona, editText3;
    Button Registar;
    RequestQueue requestQueue;
    Spinner spinerzona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rrid=getIntent().getExtras().getInt("id");
        requestQueue = Volley.newRequestQueue(EditarActivity.this);



        if(rrid!=0){
            extraer(rrid);

        }else{
            Intent inte = new Intent(EditarActivity.this,LoginActivity.class);
            startActivity(inte);
            finish();
        }

        spinerzona = findViewById(R.id.editzona);
        editnombre = findViewById(R.id.editnombre);
        editapellidos = findViewById(R.id.editarea);
        editemail = findViewById(R.id.editjefe);
        editpass = findViewById(R.id.editanotacion);
        editText3 = findViewById(R.id.editText3);
        Registar = findViewById(R.id.Registar);

        Registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actializar(rrid,editnombre.getText().toString(),editapellidos.getText().toString(),spinerzona.getSelectedItem().toString(),editText3.getText().toString(),editemail.getText().toString(),editpass.getText().toString());
            }
        });

    }

    public int cambiar(String zona){
        if(zona.equals("Bello")){return 1; }
        if(zona.equals("Buga")){return 2; }
        if(zona.equals("Cali Sur")){return 3; }
        if(zona.equals("Cemento")){return 4; }
        if(zona.equals("Chia")){return 5; }
        if(zona.equals("Floridablanca")){return 6; }
        if(zona.equals("Nobsa")){return 7; }
        if(zona.equals("Palmira")){return 8; }
        if(zona.equals("Puente Aranda")){return 9; }
        if(zona.equals("Ricaurte")){return 10; }
        if(zona.equals("Teleport")){return 11; }
        if(zona.equals("Tunja")){return 12; }
        if(zona.equals("Villavicencio")){return 13; }
        return 0;
    }

    public int cambiardos(int zona){
        if(zona==1){return 0; }
        if(zona==2){return 1; }
        if(zona==3){return 2; }
        if(zona==4){return 3; }
        if(zona==5){return 4; }
        if(zona==6){return 5; }
        if(zona==7){return 6; }
        if(zona==8){return 7; }
        if(zona==9){return 8; }
        if(zona==10){return 9; }
        if(zona==11){return 10; }
        if(zona==12){return 11; }
        if(zona==13){return 12; }
        return 13;
    }

    public String azona(int zona){
        if(zona==1){return "Bello"; }
        if(zona==2){return "Buga"; }
        if(zona==3){return "Cali Sur"; }
        if(zona==4){return "Cemento"; }
        if(zona==5){return "Chia"; }
        if(zona==6){return "Floridablanca"; }
        if(zona==7){return "Nobsa"; }
        if(zona==8){return "Palmira"; }
        if(zona==9){return "Puente Aranda"; }
        if(zona==10){return "Ricaurte"; }
        if(zona==11){return "Teleport"; }
        if(zona==12){return "Tunja"; }
        if(zona==13){return "Villavicencio"; }else{
            return "";
        }
    }

    public void  extraer(int id) {

        String HttpUrlc = "http://api-holcim.com/extraertododos.php?id="+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, HttpUrlc, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        String rnombres=null;
                        String rapellidos=null;
                        String rzona=null;
                        String rarea=null;
                        String remail=null;
                        String rpassw=null;
                        try {
                            Log.e("mensa",response.toString());
                            rzona = response.getString("zona");
                            rnombres = response.getString("nombre");
                            rapellidos = response.getString("apellidos");
                            rarea = response.getString("area");
                            remail = response.getString("email");
                            rpassw = response.getString("password");

                            int nzona=cambiardos(Integer.parseInt(rzona));
                            Log.e("mensa", String.valueOf(nzona));

                            String[] zonas = {"Bello", "Buga", "Cali Sur", "Cemento", "Chia", "Floridablanca", "Nobsa", "Palmira", "Puente Aranda", "Ricaurte", "Teleport", "Tunja", "Villavicencio"};
                            spinerzona.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_spinner_item, zonas));

                            spinerzona.setSelection(nzona);

                            editnombre.setText(rnombres);
                            editapellidos.setText(rapellidos);
                            editemail.setText(remail);
                            editpass.setText(rpassw);
                            editText3.setText(rarea);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RESPUSTA" , "Hubo error volley");
                    }
                });
        requestQueue.add(jsonObjectRequest);
        requestQueue.getCache().clear();
    }


    public void  Actializar(final int id, String ednombre, String edapellidos, String edzona, String edarea, String edemail, String edpass) {

        int zo = cambiar(edzona);

        String HttpUrl = "http://api-holcim.com/actualizar.php?id="+id+"&nombre="+ednombre+"&apellidos="+edapellidos+"&zona="+zo+"&area="+edarea+"&email="+edemail+"&password="+edpass;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        if (ServerResponse.contains("si")) {
                            Toast.makeText(getApplicationContext(), "Actualizado", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(EditarActivity.this, PerfilActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(EditarActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Problemas con la conexión", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(EditarActivity.this);
        requestQueue.add(stringRequest);
    }

}
