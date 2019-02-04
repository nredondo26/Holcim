package nredondo26.com.holcim.proyect.Activities;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Adapter.Adapter_centrosmedicos;
import nredondo26.com.holcim.proyect.Model.Atributos_centrosmedicos;

import static com.android.volley.toolbox.Volley.*;

public class Centrosmedicos extends Fragment {

    private RecyclerView rv;
    public List<Atributos_centrosmedicos> atributosList;
    public Adapter_centrosmedicos adapter;
    private RequestQueue rq;

    static String URL_LICENCIA="http://api-holcim.com/centrosmedicos.php";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_centrosmedicos,container, false);
        atributosList = new ArrayList<>();
        rv = v.findViewById(R.id.recyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        adapter = new Adapter_centrosmedicos(atributosList,getActivity());
        rv.setAdapter(adapter);
        rq = newRequestQueue(Objects.requireNonNull(getActivity()));
        consulta_centrosmedicos(String.valueOf(1));
        return v;
    }

    private void consulta_centrosmedicos(final String idplanta){
        StringRequest str = new StringRequest(Request.Method.POST,URL_LICENCIA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONArray json = new JSONArray(response);
                            for (int i=0; i < json.length(); i++)
                            {
                                JSONObject oneObject = json.getJSONObject(i);
                                String id=oneObject.getString("id");
                                String nombre=oneObject.getString("nombre");
                                String direccion=oneObject.getString("direccion");
                                String latitud=oneObject.getString("latitud");
                                String longitud=oneObject.getString("longitud");

                                Llenar(nombre,direccion,latitud,longitud);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                 Log.e("TAG",error.toString());
                 Toast.makeText(getActivity(),"Error al autenticarse por favor revise su conexión a internet",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idplanta", idplanta);
                return parametros;
            }
        };
        rq.add(str);
    }


    public void Llenar(String nom,String dir, String lat, String lon){

        Atributos_centrosmedicos centros=new Atributos_centrosmedicos(nom,dir,lat,lon);

        centros.setNombre(nom);
        centros.setDireccion(dir);
        centros.setLatitud(lat);
        centros.setLongitud(lon);

        atributosList.add(centros);
        adapter.notifyDataSetChanged();
    }

}
