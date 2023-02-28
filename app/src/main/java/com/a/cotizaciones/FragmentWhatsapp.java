package com.a.cotizaciones;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentWhatsapp extends Fragment {

    String contact = "+54 3401537292";
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_whatsapp, container, false);

        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("whatsapp://send?phone=" + contact));
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);

        return root;
    }
}
