package nredondo26.com.holcim.proyect.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import nredondo26.com.holcim.R;

public class Traslado extends AppCompatActivity {

    ConstraintLayout occidente, accidentes, mederi, kennedy, sanjuan, universitaria;

    ProgressDialog progressDialog;

    double lat_occidente = 4.629399;
    double lon_occidente = -74.135634;

    double lat_accidente = 4.630331;
    double lon_accidente = -74.130870;

    double lat_mederi = 4.623724;
    double lon_mederi = -74.0825349;

    double lat_kennedy =  4.6165621;
    double lon_kennedy = -74.1543351;

    double lat_sanjuan = 4.588418;
    double lon_sanjuan = -74.0855681;

    double lat_universitaria = 4.647356;
    double lon_universitaria = -74.1062957;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traslado);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        permisos();

        progressDialog = new ProgressDialog(Traslado.this);

        occidente= findViewById(R.id.occidente);
        accidentes= findViewById(R.id.accidentes);
        mederi= findViewById(R.id.mederi);
        kennedy= findViewById(R.id.kennedy);
        sanjuan= findViewById(R.id.sanjuan);
        universitaria= findViewById(R.id.universitaria);

        occidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Marcarruta(lat_occidente,lon_occidente);
            }
        });
        accidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Marcarruta(lat_accidente,lon_accidente);
            }
        });
        mederi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Marcarruta(lat_mederi,lon_mederi);
            }
        });
        kennedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Marcarruta(lat_kennedy,lon_kennedy);
            }
        });
        sanjuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Marcarruta(lat_sanjuan,lon_sanjuan);
            }
        });
        universitaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Marcarruta(lat_universitaria,lon_universitaria);
            }
        });
    }

    public void permisos(){

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck== PackageManager.PERMISSION_DENIED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

    }

    public void Marcarruta(final double r3, final double r4){

        progressDialog.setMessage("Obteniendo ruta, espere por favor...");
        progressDialog.show();

        LocationManager locationManager = (LocationManager) Traslado.this.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @SuppressLint("SetTextI18n")
            public void onLocationChanged(Location location) {

                progressDialog.dismiss();

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+location.getLatitude()+","+location.getLongitude()+"&daddr="+r3+","+r4));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER );
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) { Toast.makeText(getApplicationContext(),"GPS Activado",Toast.LENGTH_SHORT).show(); }
            public void onProviderDisabled(String provider) { Toast.makeText(getApplicationContext(),"GPS Desactivado",Toast.LENGTH_SHORT).show(); }

        };

        int permissionCheck = ContextCompat.checkSelfPermission(Traslado.this, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 100, locationListener);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.muni_bitacora, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intebitacora = new Intent(Traslado.this,BitacoraActivity.class);
                startActivity(intebitacora);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
