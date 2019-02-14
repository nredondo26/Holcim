package nredondo26.com.holcim.proyect.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Activities.PerfilActivity;
import nredondo26.com.holcim.proyect.Adapter.RecyclerViewAdapter;
import nredondo26.com.holcim.proyect.Model.Contact;
import java.util.Map;
import  java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class uno extends Fragment {

    String idzona;
    public String res;
    public  static  final  String URL_IP="http://api-holcim.com/ccinterno.php";
    public  static  final  String URL="http://api-holcim.com/ccp.php";
    int ID_USUARIO;
    private RequestQueue rq;
    View v;
    private RecyclerView myrecyclerview;
    private List<Contact> mData;
    private RecyclerViewAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_uno, container, false);
        myrecyclerview = v.findViewById(R.id.contac_recyclerview);

        cargarpreferencias();

        mData = new ArrayList<>();
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        myrecyclerview.setLayoutManager(lm);
        adapter = new RecyclerViewAdapter(getContext(),mData);
        myrecyclerview.setAdapter(adapter);

        rq = Volley.newRequestQueue(getContext());

        extraer();
        return v;
    }

    private void extraer(){
        StringRequest str = new StringRequest( Request.Method.POST, URL,new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject JSON = new JSONObject(response);
                    String rzona = JSON.getString("zona");

                    enviar(rzona);

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
                parametros.put("id", String.valueOf(ID_USUARIO));
                return parametros;

            }
        };
        rq.add(str);
    }

    private void cargarpreferencias() {
        SharedPreferences preferences = this.getActivity().getSharedPreferences("CREDENCIALES", Context.MODE_PRIVATE);
        int id= preferences.getInt("id", 0);
        ID_USUARIO= id;
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

                        String nombre_persona=json_objet.getString("nombre");
                        String telefono_persona=json_objet.getString("telefono");
                        llena_adapter(nombre_persona,telefono_persona);
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

    private void llena_adapter(String nombre,String telefono){
        Contact array_contact=new Contact(nombre,telefono);
        array_contact.setNombre(nombre);
        array_contact.setTelefono(telefono);
        mData.add(array_contact);
        adapter.notifyDataSetChanged();
    }


}
