package com.a.cotizaciones;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private AdaptadorFragment adaptadorFragment;

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

    }

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

    //////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
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
//////////

    public void MostrarCalendario(){
        Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int dayOfMonth = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                //dayOfMonth+"/"+(month+1)+"/"+year
                Toast.makeText(MainActivity.this,dayOfMonth+"/"+(month+1)+"/"+year,Toast.LENGTH_SHORT).show();
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