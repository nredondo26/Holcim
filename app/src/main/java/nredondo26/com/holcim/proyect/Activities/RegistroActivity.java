package nredondo26.com.holcim.proyect.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import nredondo26.com.holcim.R;


public class RegistroActivity extends AppCompatActivity {

    EditText editnombre, editapellidos, editemail, editpass;
    String editnombreholder, editapellidosholder, editemailholder, editpassholder, spinner_order_typeholder, spinner_order_type2holder;
    AppCompatSpinner spinner_order_type, spinner_order_type2;
    Button Registar;
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String HttpUrl = "http://api-holcim.com/registro.php";
    Button cargarfoto;
    Uri imageUri;
    Bitmap profilePicture;
    ImageView imagenv;
    boolean IMAGE_STATUS = false;
    String rimagen;
    private static final int PICK_IMAGE = 100;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editnombre = findViewById(R.id.editnombre);
        editapellidos = findViewById(R.id.editarea);
        editemail = findViewById(R.id.editjefe);
        editpass = findViewById(R.id.editanotacion);
        Registar = findViewById(R.id.Registar);
        cargarfoto= findViewById(R.id.cargar);
        imagenv= findViewById(R.id.imagen);
        spinner_order_type = findViewById(R.id.spinner_order_type);
        spinner_order_type2 = findViewById(R.id.spinner_order_type2);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        progressDialog = new ProgressDialog(RegistroActivity.this);

        Registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    if(!IMAGE_STATUS){
                        Toast.makeText(getApplicationContext(),"Debe seleccionar una imagen",Toast.LENGTH_SHORT).show();
                    }else{
                        Registarusuario();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Por favor complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
                }
            }
        });


        cargarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


        AppCompatSpinner spinner_order_type = findViewById(R.id.spinner_order_type);
        String[] letra = {"ZONA",
                "Chia",
                "Nobsa",
                "Puente aranda",
                "Bello",
                "Floridablanca",
                "Palmira",
                "Pipiral",
                "Cartagena",
                "Teleport",
                "Buga",
                "Ricaurte",
                "Tunja"
        };
        spinner_order_type.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_spinner_item, letra));

        AppCompatSpinner spinner_order_type2 = findViewById(R.id.spinner_order_type2);
        String[] letra1 = {"PUESTO",
                "Jefe de Brigada",
                "Jefe de Brigada",
                "Jefe de Brigada Zona I",
                "Jefe de Brigada Zona III",
                "Jefe de Brigada Zona II",
                "Líder de Brigada de Primeros Aux",
                "Líder de Brigada de Prevención y c",
                "incendios",
                "Líder de Brigada de Evacuación, b",
                "Líder de Brigada de Comunicación",
                "Responsable de la Instalación",
                "Responsable de H&S de la Instala",
                "Responsable local de Suministros",
                "Responsable local del área Legal",
                "Responsable local de RH",
                "Responsable local de IT",
                "Suplente de Jefe de Brigada",
                "Suplente Jefe de Brigada Zona III",
                "Brigadista de Evacuación/Suplente Jefe de Brigada Zona I",
                "Brigadista de Primeros Auxilios",
                "Brigadista de Prevención y Combate de Incendios",
                "Brigadista de Evacuación",
                "Brigadista de Primeros Auxilios",
                "Brigadista de Prevención y Combate de Incendios",
                "Brigadista de Evacuación",
                "Brigadista de Evacuación/Suplente Jefe de Brigada Zona II",
                "Brigadista de Primeros Auxilios",
                "Brigadista de Prevención y Combate de Incendios",
                "Brigadista de Evacuación",
                "Médico Responsable",
                "rescate",
                "Otros"
        };
        spinner_order_type2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_spinner_item, letra1));

    }

    public void CheckEditTextIsEmptyOrNot() {
        editnombreholder = editnombre.getText().toString().trim();
        editapellidosholder = editapellidos.getText().toString().trim();
        editemailholder = editemail.getText().toString().trim();
        editpassholder = editpass.getText().toString().trim();
        spinner_order_typeholder = spinner_order_type.getSelectedItem().toString();
        spinner_order_type2holder = spinner_order_type2.getSelectedItem().toString();
        CheckEditText = !TextUtils.isEmpty(editnombreholder) && !TextUtils.isEmpty(editapellidosholder) && !TextUtils.isEmpty(editemailholder) && !TextUtils.isEmpty(editpassholder) && !TextUtils.isEmpty(spinner_order_typeholder) && !TextUtils.isEmpty(spinner_order_type2holder);
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
                            Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegistroActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
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
                rimagen= convertBitmapToString(profilePicture);
                params.put("nombre", editnombreholder);
                params.put("apellidos", editapellidosholder);
                params.put("zona", spinner_order_typeholder);
                params.put("area", spinner_order_type2holder);
                params.put("email", editemailholder);
                params.put("password", editpassholder);
                params.put("imagenperfil", rimagen);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegistroActivity.this);
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
