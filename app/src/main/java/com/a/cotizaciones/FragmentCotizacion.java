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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentCotizacion extends Fragment {

    private RequestQueue queue;

    ViewPager2 viewPagerFechasCotizaciones;

    RecyclerView recyclerView;

    List<FechasCotizaciones> fechasCotizacionesList;

    List<Monedas> monedasList;

    MonedasAdapter adapter;
    FechasCotizacionesAdapter adapter1;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_cotizacion,container,false);

        recyclerView = v.findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //ArrayList<Monedas> monedas = new ObtenerDatosEndPoint().ObtenerDatosVolleyMonedas(getActivity());

        monedasList = new ArrayList<>();

        adapter = new MonedasAdapter(getContext(),monedasList);

        recyclerView.setAdapter(adapter);

        queue = Volley.newRequestQueue(getActivity());

        ObtenerDatosVolleyMonedas();
        //viewPagerFechasCotizaciones = v.findViewById(R.id.view_pagerFechasCotizaciones);
        //viewPagerFechasCotizaciones.setAdapter(new FechasCotizacionesAdapter(this.getContext(),fechasCotizacionesList));
        return v;
    }

    public void ObtenerDatosVolleyMonedas(){
        //endpoint que devuelve un JSON Array con objetos dentro
        {/* (Estructura del JSON)
            [
	            {
	            "casa":{
		                "compra":"176,99",
		                "venta":"185,99",
		                "agencia":"349",
		                "nombre":"Dolar Oficial",
		                "variacion":"0,34",
		                "ventaCero":"TRUE",
		                "decimales":"2"
		                }
	            },
	            {...
	        ] */}
        String url = "https://www.dolarsi.com/api/api.php?type=valoresprincipales";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                monedasList = new ArrayList<>();
                for (int i=0;i<response.length()-2;i++){
                    JSONObject mJsonObject =  response.getJSONObject(i);
                    JSONObject mJsonObject2 =  mJsonObject.getJSONObject("casa");
                    String nom_moneda = mJsonObject2.getString("nombre");
                    String pre_compra = mJsonObject2.getString("compra");
                    String pre_venta = mJsonObject2.getString("venta");
                    String val_variacion = mJsonObject2.has("variacion") ? mJsonObject2.getString("variacion") : "";

                    Monedas moneda = new Monedas(nom_moneda,pre_compra,pre_venta,val_variacion);
                    monedasList.add(moneda);
                }
                adapter = new MonedasAdapter(getActivity(),monedasList);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        });
        queue.add(request);
    }
    public void MostrarFecha(String fecha, String compra, String venta){

        fechasCotizacionesList = new ArrayList<>();
        ////
        FechasCotizaciones fechasCotizaciones = new FechasCotizaciones(fecha,compra,venta);
        fechasCotizacionesList.add(fechasCotizaciones);
        ////

        adapter1 = new FechasCotizacionesAdapter(this.getContext(),fechasCotizacionesList);
        recyclerView.setAdapter(adapter);

    }

}
