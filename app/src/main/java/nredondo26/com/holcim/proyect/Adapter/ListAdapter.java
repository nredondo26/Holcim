package nredondo26.com.holcim.proyect.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nredondo26.com.holcim.R;

public class ListAdapter extends ArrayAdapter<String> {

    private Activity activity;
    ArrayList<String> mensajes;

    public ListAdapter(Activity activity, ArrayList<String> mensajes) {
        super(activity, R.layout.listitem);
        this.activity = activity;
        this.mensajes = mensajes;
    }

    static class ViewHolder {
        protected TextView nameTextView;
    }

    public int getCount() {
        return mensajes.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {

        View view = null;
        LayoutInflater inflator = activity.getLayoutInflater();
        view = inflator.inflate(R.layout.listitem, null);
        final ViewHolder viewHolder = new ViewHolder();

        // *** instanciamos a los recursos
        viewHolder.nameTextView = (TextView) view.findViewById(R.id.nameTextView);

        // importante!!! establecemos el mensaje
        viewHolder.nameTextView.setText(mensajes.get(position));

        return view;

    }

}

