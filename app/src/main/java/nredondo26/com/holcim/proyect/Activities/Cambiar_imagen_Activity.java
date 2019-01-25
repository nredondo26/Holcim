package nredondo26.com.holcim.proyect.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
import nredondo26.com.holcim.R;

public class Cambiar_imagen_Activity extends AppCompatActivity {

    String rimagen;
    private static final int PICK_IMAGE = 100;
    CircleImageView imagenv;
    Button cargarfoto;
    Button Registar;
    Uri imageUri;
    Bitmap profilePicture;
    boolean IMAGE_STATUS = false;
    RequestQueue requestQueue;
    String HttpUrl = "http://api-holcim.com/actualizarimagen.php";
    int rrid;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_imagen_);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rrid=getIntent().getExtras().getInt("id");
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
                        Registarusuario();
                    }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            try {
                imageUri = data.getData();
                profilePicture = reduceBitmap(this, String.valueOf(imageUri), 500, 500);
                imagenv.setImageBitmap(profilePicture);
                IMAGE_STATUS=true;
            }catch (Exception ex){
                Log.e("ERROR" , ex.getMessage() );
            }
        }else{
            Log.e("ERROR" , "no optubo la imagen");
        }
    }

    public void Registarusuario() {
        progressDialog.setMessage("Espere....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();
                        if (ServerResponse.contains("si")) {
                            Toast.makeText(getApplicationContext(), "Imagen Actualizada         ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Cambiar_imagen_Activity.this, PerfilActivity.class);
                            intent.putExtra("id", rrid);
                            startActivity(intent);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Cambiar_imagen_Activity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getApplicationContext(), "Problemas con la conexión", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                rimagen= convertBitmapToString(profilePicture);
                params.put("id", String.valueOf(rrid));
                params.put("imagenperfil", rimagen);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Cambiar_imagen_Activity.this);
        requestQueue.add(stringRequest);
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    private String convertBitmapToString(Bitmap profilePicture) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        profilePicture.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] array = byteArrayOutputStream.toByteArray();
        String foto = Base64.encodeToString(array, Base64.DEFAULT);
        return foto;
    }

    public static Bitmap reduceBitmap(Context contexto, String uri, int maxAncho, int maxAlto) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso no encontrado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }
}
