package nredondo26.com.holcim.proyect.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
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
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import java.net.URISyntaxException;
import nredondo26.com.holcim.R;


public class RegistroActivity extends AppCompatActivity {

    EditText editnombre, editapellidos, editemail, editpass,editcodempleado;
    String editnombreholder, editapellidosholder, editemailholder, editpassholder, spinner_order_typeholder, spinner_order_type2holder,editcodempleadoholder;
    AppCompatSpinner spinner_order_type, spinner_order_type2;
    Button Registar;
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String HttpUrl = "http://api-holcim.com/registro.php";
    Button cargarfoto;
    Uri imageUri;
    ImageView imagenv;
    boolean IMAGE_STATUS = false;
    String rutaimagen;
    private static final int PICK_IMAGE = 100;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        editnombre = findViewById(R.id.editnombre);
        editcodempleado = findViewById(R.id.editcodempleado);
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
                "Bello",
                "Buga",
                "Cali Sur",
                "Cemento",
                "Chia",
                "Floridablanca",
                "Nobsa",
                "Palmira",
                "Puente Aranda",
                "Ricaurte",
                "Teleport",
                "Tunja",
                "Villavicencio"
        };
        spinner_order_type.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_spinner_item, letra));

        AppCompatSpinner spinner_order_type2 = findViewById(R.id.spinner_order_type2);
        String[] letra1 = {"Cargos",
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
                "Otros",
                "Visitante",
                "Trabajador no Holcim"
        };
        spinner_order_type2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.layout_spinner_item, letra1));

    }

    public int cambiaspiner(String zona){
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

    public void CheckEditTextIsEmptyOrNot() {
        editnombreholder = editnombre.getText().toString().trim();
        editapellidosholder = editapellidos.getText().toString().trim();
        editemailholder = editemail.getText().toString().trim();
        editpassholder = editpass.getText().toString().trim();
        editcodempleadoholder=editcodempleado.getText().toString().trim();
        spinner_order_typeholder = spinner_order_type.getSelectedItem().toString();
        spinner_order_type2holder = spinner_order_type2.getSelectedItem().toString();
        CheckEditText = !TextUtils.isEmpty(editcodempleadoholder) && !TextUtils.isEmpty(editnombreholder) && !TextUtils.isEmpty(editapellidosholder) && !TextUtils.isEmpty(editemailholder) && !TextUtils.isEmpty(editpassholder) && !TextUtils.isEmpty(spinner_order_typeholder) && !TextUtils.isEmpty(spinner_order_type2holder);
    }


    public void Registarusuario() {

        progressDialog.setMessage("Espere un momento");
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
                        progressDialog.dismiss();
                        if (response.contains("exitoso")) {
                            Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_LONG).show();
                            finish();
                            Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        if (response.contains("Codnot")) {
                            Toast.makeText(getApplicationContext(), "Codigo de empleado no existe", Toast.LENGTH_LONG).show();
                        }

                        if (response.contains("registrado")) {
                            Toast.makeText(getApplicationContext(), "Email ya existe", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(RegistroActivity.this, response, Toast.LENGTH_LONG).show();
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
        smr.addStringParam("apellidos", editapellidosholder);
        int valor = cambiaspiner(spinner_order_typeholder);
        smr.addStringParam("zona", String.valueOf(valor));
        smr.addStringParam("area", spinner_order_type2holder);
        smr.addStringParam("email", editemailholder);
        smr.addStringParam("password", editpassholder);
        smr.addStringParam("codempleado", editcodempleadoholder);
        smr.addFile("imagen", rutaimagen);

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(smr);

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