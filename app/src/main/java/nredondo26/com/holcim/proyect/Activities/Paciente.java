package nredondo26.com.holcim.proyect.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nileshp.multiphotopicker.photopicker.activity.PickImageActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nredondo26.com.holcim.R;

public class Paciente extends AppCompatActivity {

    EditText editnombre;
    EditText editarea;
    EditText editjefe;
    EditText editanotacion;
    Button benviar;
    String editnombreholder, editareaholder, editjefeholder, editanotacionholder,cod;
    Boolean CheckEditText;
    RequestQueue requestQueue;
    String valorid;
    String HttpUrl = "http://api-holcim.com/registropaciente.php";
    String HttpUrlf ="http://api-holcim.com/subirimagen.php";
    Button btnPhoto;
    ArrayList<String> listaImagenes;
    ArrayList<String> listaRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        cod=getIntent().getExtras().getString("cod");

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        editnombre= findViewById(R.id.editnombre);
        editarea= findViewById(R.id.editarea);
        editjefe= findViewById(R.id.editjefe);
        editanotacion= findViewById(R.id.editanotacion);
        benviar= findViewById(R.id.benviar);
        listaImagenes = new ArrayList<>();
        btnPhoto= findViewById(R.id.btnImagen);


        btnPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), PickImageActivity.class);
                mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 5);
                mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 1);
                startActivityForResult(mIntent, PickImageActivity.PICKER_REQUEST_CODE);
            }
        });


        benviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    Registarusuario();
                } else {
                    Toast.makeText(getApplicationContext(), "Por favor complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            return;
        }
        if (resultCode == -1 && requestCode == PickImageActivity.PICKER_REQUEST_CODE) {
            this.listaRuta = data.getExtras().getStringArrayList(PickImageActivity.KEY_DATA_RESULT);
            if (this.listaRuta != null && !this.listaRuta.isEmpty()) {
                StringBuilder sb=new StringBuilder("");
                for(int i=0;i<listaRuta.size();i++) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),  Uri.fromFile(new File(listaRuta.get(i))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                    listaImagenes.add(encodedImage);

                    sb.append(listaRuta.get(i));
                    sb.append("\n");

                }
                //Log.e("RESULTADO : ","LISTA : "+listaImagenes.size());
               // tvResult.setText(sb.toString()); // here this is textview for sample use...
            }
        }
    }

    public void CheckEditTextIsEmptyOrNot() {
        editnombreholder = editnombre.getText().toString().trim();
        editareaholder = editarea.getText().toString().trim();
        editjefeholder = editjefe.getText().toString().trim();
        editanotacionholder =  editanotacion.getText().toString().trim();
        CheckEditText = !TextUtils.isEmpty(editnombreholder) && !TextUtils.isEmpty(editareaholder) && !TextUtils.isEmpty(editjefeholder) && !TextUtils.isEmpty(editanotacionholder);
    }

    public void Registarusuario() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        valorid= ServerResponse.substring(7);

                        for(int i=0; i<listaImagenes.size();i++){
                            Registrarfoto(valorid,listaImagenes.get(i));
                        }

                        Log.e("NUMERO","NUMERO : "+valorid);

                        if (ServerResponse.contains("exitoso")) {
                            Toast.makeText(getApplicationContext(), "Reporte exitoso", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(Paciente.this, ServerResponse, Toast.LENGTH_LONG).show();
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
                params.put("nombre", editnombreholder);
                params.put("area", editareaholder);
                params.put("jefe", editjefeholder);
                params.put("anotacion",  editanotacionholder);
                params.put("cod",cod);
              //  params.put("foto", String.valueOf(listaImagenes));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Paciente.this);
        requestQueue.add(stringRequest);
    }

    public void Registrarfoto(final String idfot, final String fotof) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlf,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        if (ServerResponse.contains("exitoso")) {
                            Toast.makeText(getApplicationContext(), "Subiendo Imagen", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(Paciente.this, ServerResponse, Toast.LENGTH_LONG).show();
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
                params.put("idfo", idfot);
                params.put("foto", fotof);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Paciente.this);
        requestQueue.add(stringRequest);
    }

}
