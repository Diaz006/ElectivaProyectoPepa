package com.my.first.taller_sharedpreference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsuariosAdaptador extends RecyclerView.Adapter<UsuariosAdaptador.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Nombre, Telefono, Email;

        public ViewHolder(View itemView) {
            super(itemView);
            Nombre=(TextView)itemView.findViewById(R.id.txtBUsuario);
            Telefono=(TextView)itemView.findViewById(R.id.txtBTelefono);
            Email=(TextView)itemView.findViewById(R.id.txtBEmail);
        }
    }

    public List<UsuariosModelo> usuarioLista;

    public UsuariosAdaptador(List<UsuariosModelo> usuarioLista) {
        this.usuarioLista = usuarioLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Nombre.setText(usuarioLista.get(position).getNombre());
        holder.Telefono.setText(usuarioLista.get(position).getTelefono());
        holder.Email.setText(usuarioLista.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return usuarioLista.size();
    }
}
