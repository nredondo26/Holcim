package nredondo26.com.holcim.proyect.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import nredondo26.com.holcim.R;


public class Traslado extends AppCompatActivity {

    ConstraintLayout occidente, accidentes, mederi, kennedy, sanjuan, universitaria;

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

    double lactual ;
    double lonactual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traslado);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        occidente= findViewById(R.id.occidente);
        accidentes= findViewById(R.id.accidentes);
        mederi= findViewById(R.id.mederi);
        kennedy= findViewById(R.id.kennedy);
        sanjuan= findViewById(R.id.sanjuan);
        universitaria= findViewById(R.id.universitaria);

        occidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
                Marcarruta(lactual,lonactual,lat_occidente,lon_occidente);
            }
        });
        accidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
                Marcarruta(lactual,lonactual,lat_accidente,lon_accidente);
            }
        });
        mederi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
                Marcarruta(lactual,lonactual,lat_mederi,lon_mederi);
            }
        });
        kennedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
                Marcarruta(lactual,lonactual,lat_kennedy,lon_kennedy);
            }
        });
        sanjuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
                Marcarruta(lactual,lonactual,lat_sanjuan,lon_sanjuan);
            }
        });
        universitaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permisos();
                Marcarruta(lactual,lonactual,lat_universitaria,lon_universitaria);
            }
        });
    }

    public void permisos(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
        }
    }

    public void Marcarruta(double r1,double r2,double r3,double r4){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart();
            if (r1 != 0.0 && r2 != 0.0) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+r1+","+r2+"&daddr="+r3+","+r4));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER );
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Intentelo de nuevo",Toast.LENGTH_LONG).show();
            }
        }
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
            }
        }
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setTraslado(this);
        assert mlocManager != null;
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, Local);
    }

    @SuppressLint("SetTextI18n")
    public void setLocation(Location loc) {
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {

                lactual= loc.getLatitude();
                lonactual= loc.getLongitude();
        }
    }

    public class Localizacion implements LocationListener {
        Traslado traslado;

        public Traslado getTraslado() {
            return traslado;
        }
        public void setTraslado(Traslado traslado) {
            this.traslado = traslado;
        }
        @Override
        public void onLocationChanged(Location loc) {
             loc.getLatitude();
             loc.getLongitude();

            if(loc.getLatitude() !=0.0 && loc.getLongitude() !=0.0){
                this.traslado.setLocation(loc);
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(),"GPS Desactivado",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(),"GPS Activado",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }
}
