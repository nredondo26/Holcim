package nredondo26.com.holcim.proyect.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import nredondo26.com.holcim.R;

public class LlamarNueveonce extends AppCompatActivity {

    String idzona;
    public String res;
    public  static  final  String URL_IP="http://api-holcim.com/nueveonce.php";
    public  static  final  String URL="http://api-holcim.com/ccp.php";
    String ID_USUARIO;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llamar_nueveonce);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rq = Volley.newRequestQueue(LlamarNueveonce.this);
        cargarpreferencias();
        extraer();
    }

    private void extraer(){
        StringRequest str = new StringRequest( Request.Method.POST, URL,new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject JSON = new JSONObject(response);
                    String rzona = JSON.getString("zona");
                    if(rzona.equals("Puente aranda")) idzona="3";
                    enviar(idzona);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error->", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("id", ID_USUARIO);
                return parametros;
            }
        };
        rq.add(str);
    }

    private void cargarpreferencias() {
        SharedPreferences preferences = getSharedPreferences("CREDENCIALES", Context.MODE_PRIVATE);
        int id= preferences.getInt("id", 0);
        ID_USUARIO= String.valueOf(id);
    }

    private void enviar(final String ID_ZONA){
        StringRequest str = new StringRequest( Request.Method.POST, URL_IP,new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray json=new JSONArray(response);

                    for (int i=0; i < json.length();i++){
                        JSONObject json_objet=json.getJSONObject(i);
                        String telefono_persona=json_objet.getString("telefono");
                        String dial = "tel:" + telefono_persona;
                        LlamarNueveonce.this.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error->", error.toString());
                //progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idplanta", ID_ZONA);
                return parametros;
            }
        };
        rq.add(str);
    }

}
