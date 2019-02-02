package nredondo26.com.holcim.proyect.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
import nredondo26.com.holcim.R;

public class Cambiar_imagen_Activity extends AppCompatActivity {


    private static final int PICK_IMAGE = 100;
    CircleImageView imagenv;
    Button cargarfoto;
    Button Registar;
    Uri imageUri;
    boolean IMAGE_STATUS = false;
    RequestQueue requestQueue;
    private  RequestQueue requestQueuee;
    String HttpUrl = "http://api-holcim.com/actualizarimagen.php";
    int rrid;
    ProgressDialog progressDialog;
    String rutaimagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_imagen_);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rrid=getIntent().getExtras().getInt("id");
        requestQueuee = Volley.newRequestQueue(getApplicationContext());
        Log.e("id", String.valueOf(rrid));
        extraer(rrid);

        progressDialog = new ProgressDialog(Cambiar_imagen_Activity.this);

        cargarfoto= findViewById(R.id.cargar);
        imagenv= findViewById(R.id.imagen);
        Registar = findViewById(R.id.Registar);
        requestQueue = Volley.newRequestQueue(Cambiar_imagen_Activity.this);

        cargarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!IMAGE_STATUS){
                        Toast.makeText(getApplicationContext(),"Debe seleccionar una imagen",Toast.LENGTH_SHORT).show();
                    }else{
                        Registarusuario(rrid);
                    }
            }
        });
    }

    public void Registarusuario(int id) {

        progressDialog.setMessage("Espero un momento");
        progressDialog.show();

        try {
            rutaimagen=getPath(getApplicationContext(),imageUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("respuesta",response);
                        progressDialog.dismiss();
                        if (response.contains("si")) {
                            Toast.makeText(getApplicationContext(), "Actualizacion exitosa", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(Cambiar_imagen_Activity.this, PerfilActivity.class);
                            intent.putExtra("id", rrid);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Cambiar_imagen_Activity.this, response, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.e("error",error.toString());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        smr.addStringParam("id", String.valueOf(id));
        smr.addFile("imagen", rutaimagen);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(smr);

    }

    public void  extraer(int id) {
        String HttpUrlc = "http://api-holcim.com/extraertodo.php?id="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, HttpUrlc, null, new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {

                        String rimagenperfil=null;
                        try {

                            rimagenperfil = response.getString("imagenperfil");
                            Glide.with(Cambiar_imagen_Activity.this).load("http://api-holcim.com/img_perfil/"+rimagenperfil).into(imagenv);

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
        requestQueuee.add(jsonObjectRequest);
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imagenv.setImageURI(imageUri);
            IMAGE_STATUS=true;

            try {
                String ruta_image=getPath(this,imageUri);

                Log.e("TAG",ruta_image);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }










}
