/*  en esta class mostraremos los valores del dia de distintos tipos de dolar, datos que obtendremos
    de un endpoint que arroja un JSON.
*/
package com.a.cotizaciones;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionMoneda {

    CheckBox checkDolarOficial,checkDolarBlue,checkDolarSoja, checkDolarCCliqui,checkDolarBolsa,checkBitcoin,checkDolarTurista;
    static boolean arrayCheck[];

    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_seleccionmoneda);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);

        checkDolarOficial = (CheckBox) dialog.findViewById(R.id.checkBoxDolarOficial);
        checkDolarBlue = (CheckBox) dialog.findViewById(R.id.checkBoxDolarBlue);
        checkDolarSoja = (CheckBox) dialog.findViewById(R.id.checkBoxDolarSoja);
        checkDolarCCliqui = (CheckBox) dialog.findViewById(R.id.checkBoxDolarCCLiqui);
        checkDolarBolsa = (CheckBox) dialog.findViewById(R.id.checkBoxDolarBolsa);
        checkBitcoin = (CheckBox) dialog.findViewById(R.id.checkBoxBitcoin);
        checkDolarTurista = (CheckBox) dialog.findViewById(R.id.checkBoxDolarTurista);



        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
                dialog.dismiss();

            }
        });

        dialog.show();
    }
    public void Check(){
        arrayCheck = new boolean[6];
        if (checkDolarOficial.isChecked()){arrayCheck[0]=true;}
        else {arrayCheck[0]=false;}
        if (checkDolarBlue.isChecked()){arrayCheck[1]=true;}
        else {arrayCheck[1]=false;}
        if (checkDolarSoja.isChecked()){arrayCheck[2]=true;}
        else {arrayCheck[2]=false;}
        if (checkDolarCCliqui.isChecked()){arrayCheck[3]=true;}
        else {arrayCheck[3]=false;}
        if (checkDolarBolsa.isChecked()){arrayCheck[4]=true;}
        else {arrayCheck[4]=false;}
        if (checkBitcoin.isChecked()){arrayCheck[5]=true;}
        else {arrayCheck[5]=false;}
        if (checkDolarTurista.isChecked()){arrayCheck[6]=true;}
        else {arrayCheck[6]=false;}
    }
}