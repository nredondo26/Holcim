package nredondo26.com.holcim.proyect.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText editnombre, editapellidos, editemail, editpass , editzona, editText3;
    Button Registar;
    RequestQueue requestQueue;


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

        editnombre = findViewById(R.id.editnombre);
        editapellidos = findViewById(R.id.editarea);
        editemail = findViewById(R.id.editjefe);
        editpass = findViewById(R.id.editanotacion);
        editzona = findViewById(R.id.editzona);
        editText3 = findViewById(R.id.editText3);
        Registar = findViewById(R.id.Registar);


        Registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actializar(rrid,editnombre.getText().toString(),editapellidos.getText().toString(),editzona.getText().toString(),editText3.getText().toString(),editemail.getText().toString(),editpass.getText().toString());
            }
        });


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

                            rnombres = response.getString("nombre");
                            rapellidos = response.getString("apellidos");
                            rzona = response.getString("zona");
                            rarea = response.getString("area");
                            remail = response.getString("email");
                            rpassw = response.getString("password");

                            editnombre.setText(rnombres);
                            editapellidos.setText(rapellidos);
                            editemail.setText(remail);
                            editpass.setText(rpassw);
                            editzona.setText(rzona);
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
    }

    public void  Actializar(final int id, String ednombre, String edapellidos, String edzona, String edarea, String edemail, String edpass) {
        String HttpUrl = "http://api-holcim.com/actualizar.php?id="+id+"&nombre="+ednombre+"&apellidos="+edapellidos+"&zona="+edzona+"&area="+edarea+"&email="+edemail+"&password="+edpass;
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
