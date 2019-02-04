package nredondo26.com.holcim.proyect.Adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Model.Atributos_centrosmedicos;


public class Adapter_centrosmedicos extends RecyclerView.Adapter<Adapter_centrosmedicos.ViewHolder> {

    private List<Atributos_centrosmedicos> atributosList;
    private Context context;
    ProgressDialog progressDialog;

    public Adapter_centrosmedicos(List<Atributos_centrosmedicos> atributosList, Context context) {
        this.atributosList = atributosList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_centrosmedicos, viewGroup, false);
        return new ViewHolder(vista);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.txt_nombre.setText(atributosList.get(i).getNombre());
        viewHolder.txt_direccion.setText(atributosList.get(i).getDireccion());

        viewHolder.carV_centrosmedicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(context);
                Marcarruta(atributosList.get(i).getLatitud(),atributosList.get(i).getLongitud());

            }
        });
    }

    public void Marcarruta(final String r3, final String r4){

        progressDialog.setMessage("Obteniendo ruta, espere por favor...");
        progressDialog.show();

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {
            @SuppressLint("SetTextI18n")
            public void onLocationChanged(Location location) {
                progressDialog.dismiss();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+location.getLatitude()+","+location.getLongitude()+"&daddr="+r3+","+r4));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_LAUNCHER );
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(intent);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) { Toast.makeText(context,"GPS Activado",Toast.LENGTH_SHORT).show(); }
            public void onProviderDisabled(String provider) { Toast.makeText(context,"GPS Desactivado",Toast.LENGTH_SHORT).show(); }

        };

        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 100, locationListener);
    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_nombre;
        TextView txt_direccion;
        CardView carV_centrosmedicos;

        public ViewHolder(View item){
            super(item);
            txt_nombre = item.findViewById(R.id.nombrecm);
            txt_direccion = item.findViewById(R.id.direccion);
            carV_centrosmedicos = item.findViewById(R.id.carV_centrosmedicos);

        }
    }


}
