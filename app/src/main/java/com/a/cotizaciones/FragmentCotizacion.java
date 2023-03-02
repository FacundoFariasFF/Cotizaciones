package com.a.cotizaciones;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class FragmentCotizacion extends Fragment {

    /*private static TextView tv_fecha;
    private static TextView tv_compra;
    private static TextView tv_venta;*/
    private ViewPager2 viewPagerFechasCotizaciones;
    FechasCotizaciones adapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_cotizacion,container,false);

        //viewPagerFechasCotizaciones = viewPagerFechasCotizaciones.findViewById(R.id.view_pagerFechasCotizaciones);

        return v;
    }
    ///
     public class AdaptadorFechaCotizaciones extends RecyclerView.Adapter<AdaptadorFechaCotizaciones.AdaptadorCotizacionHolder>{
        Context context;
        List<FechasCotizaciones> fechasCotizacionesList;



        public AdaptadorFechaCotizaciones(Context context, List<FechasCotizaciones> fechasCotizacionesList){
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
                //tv_fecha = v.findViewById(R.id.txt_fecha);
                fecha = itemView.findViewById(R.id.txt_fecha);
                compra = itemView.findViewById(R.id.txt_compra);
                venta = itemView.findViewById(R.id.txt_venta);
                cardView = itemView.findViewById(R.id.cardview);
            }

        }

    }

    ///
   public void MostrarFecha(String fecha, String compra, String venta){

       /* tv_fecha.setText("Fecha: "+fecha);
        tv_compra.setText("Precio de Compra: "+compra);
        tv_venta.setText("Precio de Venta: "+venta);*/

    }

}
