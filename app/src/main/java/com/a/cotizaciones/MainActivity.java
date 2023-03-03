package com.a.cotizaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {


    private DatePickerDialog.OnDateSetListener listener;
    MenuItem itemMonedas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        // Asignmos el fragment FragmentWhatsapp al FramrLayout frame
      // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new FragmentMonedas()).commit();

       // RangoFecha("00-00-0000");


    }

// Fragment


 // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        itemMonedas = menu.findItem(R.id.menu_monedas);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.menu_calendario:
                Toast.makeText(MainActivity.this,"abrir calendario",Toast.LENGTH_SHORT).show();
                MostrarCalendario();
                return true;
            case R.id.menu_monedas:
                Toast.makeText(MainActivity.this,"abrir monedas",Toast.LENGTH_SHORT).show();
                SeleccionMoneda alert = new SeleccionMoneda();
                alert.showDialog(MainActivity.this, "mensaje de prueba");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, new FragmentMonedas()).commit();

                return true;
            case R.id.menu_whatsapp:
                Toast.makeText(MainActivity.this,"abrir whatsapp",Toast.LENGTH_SHORT).show();
                //viewPager2.setCurrentItem(1,true);
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
///
    public void RangoFecha(String fechaSelecAux){
        ObtenerDatosEndPoint obtenerDatosEndPoint = new ObtenerDatosEndPoint();

        String fechaMenosSieteDias ="00-00-0000";
        Date datefechaSelecAux;
        Date datefechaMenosSieteDias;
        int op = 1;
        Calendar calendarioAux = Calendar.getInstance();
        //Formato de la fecha
        DateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");

        if (fechaSelecAux.equals("00-00-0000")){
            calendarioAux.add(Calendar.DATE, -7);
            fechaMenosSieteDias= (formateador.format(calendarioAux.getTime()));

            obtenerDatosEndPoint.ObtenerDatosVolleyFechas(this, fechaMenosSieteDias);
        } else {
            SimpleDateFormat formateador1 = new SimpleDateFormat("dd-MM-yyyy");
            try {
                datefechaSelecAux  = formateador1.parse(fechaSelecAux);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            try {
                datefechaMenosSieteDias  = formateador1.parse(fechaMenosSieteDias);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            //si la fecha seleccionada es menor a la fecha seleccionada anterior menos 7 dias
            if (datefechaSelecAux.before(datefechaMenosSieteDias)){
                // asignamos a fechaMenosSieteDias la fecha seleccionada menos 7 dias
                fechaMenosSieteDias= (formateador.format(datefechaSelecAux.getTime()-7));

                obtenerDatosEndPoint.ObtenerDatosVolleyFechas(this, fechaMenosSieteDias);
            }
        }
    }
// calendario
    public void MostrarCalendario(){
        String fechaHoy;
        Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int dayOfMonth = calendario.get(Calendar.DAY_OF_MONTH);
        fechaHoy = dayOfMonth + "/" + (month+1) + "/" + year;
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                String fechaSelec, fechaSelecAux;
                String dia = "";
                String mes = "";
                String anio = String.valueOf(year);
                // sentencias condicionales para corregir el formato (dd,mm,yy) de fecha que arroja el DatePicker
                // para que coincida con la fecha de la db (el json tien formato de dos digitos en los meses (mes 01) y el DatePicker(mes 1) no)
                if (dayOfMonth<10 || (month+1)<10){
                    if (dayOfMonth<10 && (month+1)<10) {
                        dia = "0"+dayOfMonth;
                        mes = "0"+(month+1);
                    } else if (dayOfMonth<10){
                        dia = "0"+dayOfMonth;
                        mes = ""+(month +1);
                    } else if((month+1)<10){
                        dia = ""+dayOfMonth;
                        mes = "0"+(month+1);
                    }
                } else {
                    dia = ""+dayOfMonth;
                    mes = ""+(month+1);
                }
                //tener en cuenta  usamos "/" o "-" ya que usamos fechaSelec para comparar drectamente con el contenido del Json (hay que poner el mismo que use el Json)
                fechaSelec = dia + "/" + mes + "/" + anio;
                //fechaSelecAux tiene este formato "dd-mm-aaaa" ya que se utiliza para el URL del Json
                fechaSelecAux = dia + "-" + mes + "-" + anio;

                RangoFecha(fechaSelecAux);
                AdminSQLiteOpenHelper.getInstance(getApplicationContext()).Buscar(fechaSelec);

                Toast.makeText(MainActivity.this,dayOfMonth+"/"+(month+1)+"/"+year,Toast.LENGTH_SHORT).show();
                if (!fechaSelec.equals(fechaHoy)){
                    itemMonedas.setVisible(false);
                }else{
                    itemMonedas.setVisible(true);
                }

            }
        },year,month,dayOfMonth);

        calendario.set(2002, 3, 9);//Year,Mounth -1,Day
        dialog.getDatePicker().setMinDate(calendario.getTimeInMillis());
        calendario.set(year,month,dayOfMonth);
        dialog.getDatePicker().setMaxDate(calendario.getTimeInMillis());

        dialog.show();
    }

/////////////
}