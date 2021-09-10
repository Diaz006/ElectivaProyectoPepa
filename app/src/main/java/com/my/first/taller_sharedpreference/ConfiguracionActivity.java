package com.my.first.taller_sharedpreference;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class ConfiguracionActivity extends AppCompatActivity {
    TextView language_dialog, hellomundo;
    boolean lang_selected = true;
    Context context;
    Resources resources;
    private TextView txt_bienvenida;
    private Button btn_cambiar_idioma;
    private Intent intent;
    Button btnSalirconfi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        txt_bienvenida = (TextView) findViewById(R.id.textView);
        btn_cambiar_idioma = (Button) findViewById(R.id.button);
        btnSalirconfi = (Button) findViewById(R.id.btnsalirconfi);

        setDayNight();

        intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName("com.android.settings", "com.android.settings.LanguageSettings");

        btn_cambiar_idioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
            }
        });
        /*
        language_dialog = (TextView) findViewById(R.id.txtdialog_language);
        hellomundo = (TextView) findViewById(R.id.txtHola);

        language_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] language = {"ESPAÑOL", "ENGLISH"};

                int checkedItem;

                if (lang_selected){
                    checkedItem=0;
                }else{
                    checkedItem=1;
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionActivity.this);

                builder.setTitle("Selecciona un Idioma")
                        .setSingleChoiceItems(language, checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                language_dialog.setText(language[which]);
                                if(language[which].equals("ESPAÑOL")){
                                    context = LocaleHelper.setLocale(ConfiguracionActivity.this, "es");
                                    resources = context.getResources();

                                    hellomundo.setText(resources.getString(R.string.Activity_SALUDO));

                                }
                                if (language[which].equals("ENGLISH")){
                                    context = LocaleHelper.setLocale(ConfiguracionActivity.this, "en");
                                    resources = context.getResources();

                                    hellomundo.setText(resources.getString(R.string.Activity_SALUDO));

                                }

                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();
            }
        });*/

        SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        final Switch swi = findViewById(R.id.switema);

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
                setDayNight();
            }
        });

        btnSalirconfi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ActivityPrincipal.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void setDayNight(){
        SharedPreferences sp = getSharedPreferences("SP", MODE_PRIVATE);
        int theme = sp.getInt("Theme", 1);
        if(theme==0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}