package com.my.first.taller_sharedpreference.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.my.first.taller_sharedpreference.ActivityPrincipal;
import com.my.first.taller_sharedpreference.R;
import com.my.first.taller_sharedpreference.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        final String Urlv= "https://lugarvacunacion.cne.gob.ec";
        final String UrlC= "https://sgrdacaa-admision.msp.gob.ec/hcue/paciente/certificadovacuna/public/index";

        final Button btnLugarV = root.findViewById(R.id.btnLugarVacunacion);
        final Button btnCertificadoV = root.findViewById(R.id.btnCertificadoVacunacion);

        btnLugarV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link = Uri.parse(Urlv);
                Intent i = new Intent(Intent.ACTION_VIEW, _link);
                startActivity(i);

            }
        });

        btnCertificadoV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri _link = Uri.parse(UrlC);
                Intent i = new Intent(Intent.ACTION_VIEW, _link);
                startActivity(i);

            }
        });

        final ActivityPrincipal ma = (ActivityPrincipal) getActivity();

        SharedPreferences sp = ma.getSharedPreferences("SP", ma.MODE_PRIVATE);
        ma.setDayNight();
        SharedPreferences.Editor editor = sp.edit();
        final Switch swi = root.findViewById(R.id.switema);

        int theme = sp.getInt("Theme", 1);
        if (theme==1){
            swi.setChecked(false);
        }else{
            swi.setChecked(true);
        }
        swi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swi.isChecked()){
                    //((MainActivity)getActivity()).setDayNight(0);
                    editor.putInt("Theme",0);
                }else{
                    //((MainActivity)getActivity()).setDayNight(1);
                    editor.putInt("Theme", 1);
                }
                editor.commit();
                ma.setDayNight();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}