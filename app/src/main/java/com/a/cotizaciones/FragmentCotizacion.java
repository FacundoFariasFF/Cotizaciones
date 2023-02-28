package com.a.cotizaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCotizacion extends Fragment {

    private static TextView tv_fecha;
    private static TextView tv_compra;
    private static TextView tv_venta;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_cotizacion,container,false);

        tv_fecha = v.findViewById(R.id.txt_fecha);
        tv_compra = v.findViewById(R.id.txt_compra);
        tv_venta = v.findViewById(R.id.txt_venta);

        return v;
    }
    public void MostrarFecha(String fecha, String compra, String venta){

        tv_fecha.setText("Fecha: "+fecha);
        tv_compra.setText("Precio de Compra: "+compra);
        tv_venta.setText("Precio de Venta: "+venta);

    }

}
