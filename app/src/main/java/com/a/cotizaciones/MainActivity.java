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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private AdaptadorFragment adaptadorFragment;

    private DatePickerDialog.OnDateSetListener listener;
    MenuItem itemMonedas;

    String fechaHoy,fechaSelec, fechaSelecAux= "";
    static String fechaMenosSieteDias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        viewPager2= findViewById(R.id.view_pager2);
        adaptadorFragment=new AdaptadorFragment(getSupportFragmentManager(),getLifecycle());
        //cuando se crea un objeto de clase AdaptadorFragment le pasamos el FragmentManager y el ciclo de vida del activity
        viewPager2.setAdapter(adaptadorFragment);



        RangoFecha();
        ObtenerDatosEndPoint obtenerDatosEndPoint = new ObtenerDatosEndPoint();
        obtenerDatosEndPoint.ObtenerDatosVolley(this);

    }
    public void RangoFecha(){
        Calendar calendarioAux = Calendar.getInstance();
        //Formato de la fecha
        DateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        if (fechaSelecAux.equals("")){
            calendarioAux.add(Calendar.DATE, -7);
            fechaMenosSieteDias= (formateador.format(calendarioAux.getTime()));

        } else {
            try {
                calendarioAux.setTime(formateador.parse(fechaSelecAux));
                calendarioAux.add(Calendar.DATE, -7);
                fechaMenosSieteDias = (formateador.format(calendarioAux.getTime()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
// Fragment
    class AdaptadorFragment extends FragmentStateAdapter{
        public AdaptadorFragment(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 1: return new FragmentWhatsapp();
                default: return new FragmentCotizacion();
            }
        }
        @Override
        //devuelve la cantidad de fragmentos que usamos
        public int getItemCount() {
            return 2;
        }
    }

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
                return true;
            case R.id.menu_whatsapp:
                Toast.makeText(MainActivity.this,"abrir whatsapp",Toast.LENGTH_SHORT).show();
                viewPager2.setCurrentItem(1,true);
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }
// calendario
    public void MostrarCalendario(){
        Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int dayOfMonth = calendario.get(Calendar.DAY_OF_MONTH);
        fechaHoy = dayOfMonth + "/" + (month+1) + "/" + year;
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
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
                //tener en cuenta  usamos "/" o "-" ya que usamos fechaSelec para comparar drectamente con el Json (hay que poner el mismo que use el Json)
                fechaSelec = dia + "/" + mes + "/" + anio;
                fechaSelecAux = dia + "-" + mes + "-" + anio;

                RangoFecha();
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