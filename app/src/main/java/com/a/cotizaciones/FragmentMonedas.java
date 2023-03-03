package com.a.cotizaciones;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentMonedas extends Fragment {
    private RequestQueue queue;

    RecyclerView recyclerView;
    List<Monedas> monedasList;
    MonedasAdapter adapter;

    View rootView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_monedas,container,false);

        recyclerView = rootView.findViewById(R.id.recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        monedasList = new ArrayList<>();

        adapter = new MonedasAdapter(getActivity(),monedasList);

        recyclerView.setAdapter(adapter);

        queue = Volley.newRequestQueue(getActivity());
        SeleccionMoneda seleccionMoneda = new SeleccionMoneda();
        seleccionMoneda.Check();
        ObtenerDatosVolleyMonedas();


        return rootView;
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
}
