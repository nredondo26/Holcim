package nredondo26.com.holcim.proyect.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import java.util.HashMap;
import java.util.Map;
import nredondo26.com.holcim.R;

public class LoginActivity extends AppCompatActivity {

    EditText Email, Password;
    Button LoginButton, register;
    RequestQueue requestQueue;
    String EmailHolder, PasswordHolder;
    ProgressDialog progressDialog;
    String HttpUrl = "http://api-holcim.com/login.php";
    Boolean CheckEditText;
    private  RequestQueue requestQueuee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Email = findViewById(R.id.editText);
        Password = findViewById(R.id.editText1);
        LoginButton = findViewById(R.id.entrar);
        register = findViewById(R.id.register);
        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueuee = Volley.newRequestQueue(getApplicationContext());
        progressDialog = new ProgressDialog(LoginActivity.this);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    UserLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Por favor complete todos los campos del formulario.", Toast.LENGTH_LONG).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }
    public void UserLogin() {

        progressDialog.setMessage("Espere...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onResponse(String ServerResponse) {
                        progressDialog.dismiss();
                        if (ServerResponse.contains("exito")) {
                            Toast.makeText(LoginActivity.this, "Sesión exitosa", Toast.LENGTH_LONG).show();
                            extraerdatos(EmailHolder,PasswordHolder);
                        } else {
                            Toast.makeText(LoginActivity.this, ServerResponse, Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Problemas con la conexión", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", EmailHolder);
                params.put("password", PasswordHolder);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }

    public void CheckEditTextIsEmptyOrNot() {
        EmailHolder = Email.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();
        CheckEditText = !TextUtils.isEmpty(EmailHolder) && !TextUtils.isEmpty(PasswordHolder);
    }

    public void  extraerdatos(final String email, final String pass) {
        String HttpUrlc = "http://api-holcim.com/consultaruser.php?email="+email+"&password="+pass;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, HttpUrlc, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int id;
                        try {
                            id = response.getInt("id");
                            Intent intent = new Intent(LoginActivity.this, PerfilActivity.class);
                            intent.putExtra("id", id);
                            Log.e("mensaje","Mensaje: "+id);
                            guardarpreferencias(id);
                            startActivity(intent);
                            finish();
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

    private void guardarpreferencias(int id){
        SharedPreferences preferences = getSharedPreferences("CREDENCIALES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putInt("id",id);
        editor.apply();
    }


}