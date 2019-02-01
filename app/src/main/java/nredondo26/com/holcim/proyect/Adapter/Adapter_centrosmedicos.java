package nredondo26.com.holcim.proyect.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Model.Atributos_centrosmedicos;

public class Adapter_centrosmedicos extends RecyclerView.Adapter<Adapter_centrosmedicos.ViewHolder> {

    private List<Atributos_centrosmedicos> atributosList;
    private Context context;

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
      //  viewHolder.txt_latitud.setText("Latitud: "+atributosList.get(i).getLatitud());
        // viewHolder.txt_longitd.setText("Longitud: "+atributosList.get(i).getLongitud());

        viewHolder.carV_centrosmedicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent=new Intent(context,Dispositivos_x_licenmcias.class);
                intent.putExtra("id_licencia", atributosList.get(i).id_licencia);
                intent.putExtra("licencia", atributosList.get(i).licencia);
                intent.putExtra("email_reporte1", atributosList.get(i).email_reporte1);
                intent.putExtra("email_reporte2", atributosList.get(i).email_reporte2);
                intent.putExtra("password", atributosList.get(i).password_licencia);
                context.startActivity(intent);*/

            }
        });
    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_nombre;
       // TextView txt_latitud;
       // TextView txt_longitd;

        CardView carV_centrosmedicos;

        public ViewHolder(View item){
            super(item);

            txt_nombre = item.findViewById(R.id.nombrecm);
          //  txt_latitud = item.findViewById(R.id.latitud);
           // txt_longitd = item.findViewById(R.id.longitud);
            carV_centrosmedicos = item.findViewById(R.id.carV_centrosmedicos);

        }
    }


}
