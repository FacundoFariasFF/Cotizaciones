/*  en esta class mostraremos los valores del dia de distintos tipos de dolar, datos que obtendremos
    de un endpoint que arroja un JSON.
*/
package com.a.cotizaciones;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

public class ObtenerDatosEndPoint {
    private RequestQueue queue;
    public void ObtenerDatosVolleyFechas(Context context, String fechaMenosSieteDias){
        queue = Volley.newRequestQueue(context);
        //endpoint que contiene los valores historicos del Dolar Oficial.
        {/* (Estructura del JSON)(JSON Array con Arrays dentro)
            [
	            ["Fecha","Compra","Venta"],
	            ["05-01-2023","177,25","186,25"],
	            ["04-01-2023","177,22","186,22"],
	            ["03-01-2023","176,99","185,99"]
            ]
        */}
        //al final del url se puede modificar la fecha para obtener menos rango de datos
        // Ejemplo: (https://mercados.ambito.com//dolar/formal/historico-general/03-01-2023/06-01-2023)

        String fechaMin= fechaMenosSieteDias;
        String fechaMax = "01-01-2030";
        String url = "https://mercados.ambito.com//dolar/formal/historico-general/"+fechaMin+"/"+fechaMax;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                String fecha ;
                String compra;
                String venta;
                for (int i = 1; i<response.length(); i++){
                    JSONArray mJsonArray = response.getJSONArray(i);
                    fecha = mJsonArray.getString(0);
                    compra = mJsonArray.getString(1);
                    venta = mJsonArray.getString(2);
                    //recorremos el JSON y enviamos a Registrar() los datos para cargar una Base de Datos.
                    AdminSQLiteOpenHelper.getInstance(context).Registrar(fecha,compra,venta);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        });
        queue.add(request);
    }



}