package nredondo26.com.holcim.proyect.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.List;
import nredondo26.com.holcim.R;
import nredondo26.com.holcim.proyect.Model.Contact;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Contact> mData;

    public RecyclerViewAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        vHolder.item_contact.setOnClickListener(new View.OnClickListener() {

            @SuppressLint({"ShowToast", "MissingPermission"})
            @Override
            public void onClick(View v) {
                String numero= (String) vHolder.tv_telefono.getText();
                numero = numero.replace("#","");
                String dial = "tel:"+numero ;
                Log.e("TAG","numero: "+numero);
                mContext.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));

            }
        });

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_nombre.setText(mData.get(position).getNombre());
        holder.tv_telefono.setText(mData.get(position).getTelefono());

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout item_contact;
        private TextView tv_nombre;
        private TextView tv_telefono;

        MyViewHolder(View itemView) {
            super(itemView);

            item_contact = itemView.findViewById(R.id.item_contact_id);
            tv_nombre = itemView.findViewById(R.id.nombreresponsable);
            tv_telefono = itemView.findViewById(R.id.telefono);
        }
    }

}
