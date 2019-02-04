package nredondo26.com.holcim.proyect.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import java.net.URISyntaxException;


import nredondo26.com.holcim.R;

public class Paciente extends AppCompatActivity {

    EditText editnombre;
    EditText editarea;
    EditText editjefe;
    EditText editanotacion;
    Button benviar;
    String editnombreholder, editareaholder, editjefeholder, editanotacionholder,cod;
    int idusaurio;
    Boolean CheckEditText;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String HttpUrl = "http://api-holcim.com/registropaciente.php";

    private static final int PICK_IMAGE1 = 101;
    private static final int PICK_IMAGE2 = 102;
    private static final int PICK_IMAGE3 = 103;
    Uri imageUri1,imageUri2,imageUri3;
    ImageView img1;
    ImageView img2;
    ImageView img3;
    boolean IMAGE_STATUS1 = false;
    boolean IMAGE_STATUS2 = false;
    boolean IMAGE_STATUS3 = false;
    String rutaimagen1;
    String rutaimagen2;
    String rutaimagen3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cod=getIntent().getExtras().getString("cod");
        idusaurio=getIntent().getExtras().getInt("id");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        progressDialog = new ProgressDialog(Paciente.this);

        editnombre= findViewById(R.id.editnombre);
        editarea= findViewById(R.id.editarea);
        editjefe= findViewById(R.id.editjefe);
        editanotacion= findViewById(R.id.editanotacion);
        benviar= findViewById(R.id.benviar);

        img1= findViewById(R.id.img1);
        img2= findViewById(R.id.img2);
        img3= findViewById(R.id.img3);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(PICK_IMAGE1);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(PICK_IMAGE2);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(PICK_IMAGE3);
            }
        });

        benviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    if(IMAGE_STATUS1==true & IMAGE_STATUS2==true & IMAGE_STATUS3==true){
                        Registarusuario1();
                    }
                    if(IMAGE_STATUS1==true & IMAGE_STATUS2==true & IMAGE_STATUS3==false){
                        Registarusuario2();
                    }
                    if(IMAGE_STATUS1==true & IMAGE_STATUS2==false & IMAGE_STATUS3==false){
                        Registarusuario3();
                    }

                    if(IMAGE_STATUS1==false & IMAGE_STATUS2==false & IMAGE_STATUS3==false){
                        Registarusuario4();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Por favor complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void openGallery(int pick){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, pick);

    }

    public void Registarusuario1() {

        progressDialog.setMessage("Espere un momento");
        progressDialog.show();

        try {
                rutaimagen1=getPath(getApplicationContext(),imageUri1);
                rutaimagen2=getPath(getApplicationContext(),imageUri2);
                rutaimagen3=getPath(getApplicationContext(),imageUri3);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.contains("exitoso")) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(Paciente.this, PerfilActivity.class);
                            intent.putExtra("id", idusaurio);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Paciente.this, response, Toast.LENGTH_LONG).show();
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
        smr.addStringParam("nombre", editnombreholder);
        smr.addStringParam("anotacion", editanotacionholder);
        smr.addStringParam("area", editareaholder);
        smr.addStringParam("jefe", editjefeholder);
        smr.addStringParam("cod", cod);
        smr.addStringParam("idusuario", String.valueOf(idusaurio));
        smr.addFile("img1", rutaimagen1);
        smr.addFile("img2", rutaimagen2);
        smr.addFile("img3", rutaimagen3);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(smr);

    }

    public void Registarusuario2() {

        progressDialog.setMessage("Espere un momento");
        progressDialog.show();

        try {
            rutaimagen1=getPath(getApplicationContext(),imageUri1);
            rutaimagen2=getPath(getApplicationContext(),imageUri2);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.contains("exitoso")) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(Paciente.this, PerfilActivity.class);
                            intent.putExtra("id", idusaurio);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Paciente.this, response, Toast.LENGTH_LONG).show();
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
        smr.addStringParam("nombre", editnombreholder);
        smr.addStringParam("anotacion", editanotacionholder);
        smr.addStringParam("area", editareaholder);
        smr.addStringParam("jefe", editjefeholder);
        smr.addStringParam("cod", cod);
        smr.addStringParam("idusuario", String.valueOf(idusaurio));
        smr.addFile("img1", rutaimagen1);
        smr.addFile("img2", rutaimagen2);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(smr);

    }

    public void Registarusuario3() {

        progressDialog.setMessage("Espere un momento");
        progressDialog.show();

        try {
            rutaimagen1=getPath(getApplicationContext(),imageUri1);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.contains("exitoso")) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(Paciente.this, PerfilActivity.class);
                            intent.putExtra("id", idusaurio);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Paciente.this, response, Toast.LENGTH_LONG).show();
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
        smr.addStringParam("nombre", editnombreholder);
        smr.addStringParam("anotacion", editanotacionholder);
        smr.addStringParam("area", editareaholder);
        smr.addStringParam("jefe", editjefeholder);
        smr.addStringParam("cod", cod);
        smr.addStringParam("idusuario", String.valueOf(idusaurio));
        smr.addFile("img1", rutaimagen1);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(smr);

    }

    public void Registarusuario4() {

        progressDialog.setMessage("Espere un momento");
        progressDialog.show();

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.contains("exitoso")) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(Paciente.this, PerfilActivity.class);
                            intent.putExtra("id", idusaurio);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Paciente.this, response, Toast.LENGTH_LONG).show();
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
        smr.addStringParam("nombre", editnombreholder);
        smr.addStringParam("anotacion", editanotacionholder);
        smr.addStringParam("area", editareaholder);
        smr.addStringParam("jefe", editjefeholder);
        smr.addStringParam("cod", cod);
        smr.addStringParam("idusuario", String.valueOf(idusaurio));

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(smr);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK ){

            if(requestCode == PICK_IMAGE1){
                imageUri1 = data.getData();
                img1.setImageURI(imageUri1);
                IMAGE_STATUS1=true;
            }
            if(requestCode == PICK_IMAGE2){
                    imageUri2 = data.getData();
                    img2.setImageURI(imageUri2);
                    IMAGE_STATUS2=true;
            }

             if(requestCode == PICK_IMAGE3){
                        imageUri3 = data.getData();
                        img3.setImageURI(imageUri3);
                        IMAGE_STATUS3=true;
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

    public void CheckEditTextIsEmptyOrNot() {
        editnombreholder = editnombre.getText().toString().trim();
        editareaholder = editarea.getText().toString().trim();
        editjefeholder = editjefe.getText().toString().trim();
        editanotacionholder =  editanotacion.getText().toString().trim();
        CheckEditText = !TextUtils.isEmpty(editnombreholder) && !TextUtils.isEmpty(editareaholder) && !TextUtils.isEmpty(editjefeholder) && !TextUtils.isEmpty(editanotacionholder);
    }



}
