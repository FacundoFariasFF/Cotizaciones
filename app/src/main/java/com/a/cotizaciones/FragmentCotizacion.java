package com.a.cotizaciones;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class FragmentCotizacion extends Fragment {

    ViewPager2 viewPagerFechasCotizaciones;

    RecyclerView recyclerView;

    List<FechasCotizaciones> fechasCotizacionesList;

    FechasCotizacionesAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_cotizacion,container,false);

        recyclerView = v.findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //viewPagerFechasCotizaciones = v.findViewById(R.id.view_pagerFechasCotizaciones);
        //viewPagerFechasCotizaciones.setAdapter(new FechasCotizacionesAdapter(this.getContext(),fechasCotizacionesList));
        return v;
    }
   public void MostrarFecha(String fecha, String compra, String venta){

        fechasCotizacionesList = new ArrayList<>();
        ////
        FechasCotizaciones fechasCotizaciones = new FechasCotizaciones(fecha,compra,venta);
        fechasCotizacionesList.add(fechasCotizaciones);
        ////

        adapter = new FechasCotizacionesAdapter(this.getContext(),fechasCotizacionesList);
        recyclerView.setAdapter(adapter);

    }

}
