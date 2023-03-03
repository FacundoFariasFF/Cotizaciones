package com.a.cotizaciones;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FechasCotizacionesAdapter extends RecyclerView.Adapter<FechasCotizacionesAdapter.AdaptadorCotizacionHolder>{
    Context context;
    List<FechasCotizaciones> fechasCotizacionesList;

    public FechasCotizacionesAdapter(Context context, List<FechasCotizaciones> fechasCotizacionesList){
        this.context = context;
        this.fechasCotizacionesList = fechasCotizacionesList;
    }
    @NonNull
    @Override
    public AdaptadorCotizacionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutfechacotizaciones,parent,false);
        AdaptadorCotizacionHolder viewHolder = new AdaptadorCotizacionHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCotizacionHolder holder, int position) {
        FechasCotizaciones fechasCotizaciones = fechasCotizacionesList.get(position);
        holder.fecha.setText(fechasCotizaciones.getFecha());
        holder.compra.setText("Precio de Compra: " +  fechasCotizaciones.getCompra());
        holder.venta.setText("Precio de Venta: " + fechasCotizaciones.getVenta());

    }

    @Override
    public int getItemCount() {
        return fechasCotizacionesList.size();
    }

    public class AdaptadorCotizacionHolder extends RecyclerView.ViewHolder{

        TextView fecha,compra,venta;
        CardView cardView;

        public AdaptadorCotizacionHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.txt_fecha);
            compra = itemView.findViewById(R.id.txt_compra);
            venta = itemView.findViewById(R.id.txt_venta);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}