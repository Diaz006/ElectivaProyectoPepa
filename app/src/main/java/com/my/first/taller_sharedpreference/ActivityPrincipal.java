package com.my.first.taller_sharedpreference;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.my.first.taller_sharedpreference.databinding.ActivityPrincipalBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityPrincipal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalBinding binding;

    public TextView txtUsuario;
    String Usuario;

    RequestQueue requestQueue;

    //SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);

    //SharedPreferences preferences;
    //SharedPreferences.Editor editor;

    //Obtiene referencia de TextView usado como header.
    TextView profileName;
    //Asigna texto a TextView.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);

        buscarUsuario("http://192.168.100.7/ProyectoAndroid2/buscar_usuario.php?email="+preferences.getString("usuario","")+"");
        //navigationView = (NavigationView) findViewById(R.id.nav_host_fragment_content_activity_principal);



        recuperarPreferenciaas();
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //preferences = getSharedPreferences("sesiones",Context.MODE_PRIVATE);
        //editor = preferences.edit();

        //Bundle datos = getIntent().getExtras();
        //String usuario = datos.getString("usuario");
        //Toast.makeText(ActivityPrincipal.this,"Bienvenido Usuario: "+usuario,Toast.LENGTH_SHORT).show();

        setSupportActionBar(binding.appBarActivityPrincipal.toolbar);

        binding.appBarActivityPrincipal.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                /*if(preferences.getBoolean("Sesion",false)){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityPrincipal.this);
                    alerta.setMessage("Desea Cerrar la Sesión?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ActivityPrincipal.this, "La Sesión fue Cerrada: ",Toast.LENGTH_SHORT).show();
                                    //editor.putBoolean("Sesion",false);
                                    //editor.apply();
                                    //editor.clear();
                                    SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                                    preferences.edit().clear().commit();

                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Cerrar Sesión");
                    titulo.show();

                }else {
                    finish();
                } */
                //SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();

                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_activity_principal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        profileName = navigationView.getHeaderView(0).findViewById(R.id.txtUsuarioAB);
        //TxtUsuario.setText(preferences.getString("usuario",""));

    }

    private void recuperarPreferenciaas(){
        //txtUsuario=findViewById(R.id.txtUsuarioA);
        //SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        //TxtUsuario.setText(preferences.getString("usuario",""));
        //txtUsuario.setText("Bryan Diaz");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_activity_principal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.ad_usuarios:
                Intent adusuario = new Intent(getApplicationContext(), AdminUsuarios.class);
                Toast.makeText(this, "ADMINISTRADOR DE USUARIOS", Toast.LENGTH_SHORT).show();
                startActivity(adusuario);
                return true;

            case R.id.mos_usuarios:
                Intent mostrarUsuarios = new Intent(getApplicationContext(), UsuariosActivity.class);
                Toast.makeText(this, "LISTAS DE USUARIOS", Toast.LENGTH_SHORT).show();
                startActivity(mostrarUsuarios);
                return true;

            case R.id.menu_usuariosa:
                Toast.makeText(this, "Configuracion", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public void showPopup (View v){
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.activity_principal, popup.getMenu());
        popup.show();
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        if (Usuario.equals("ADMIN")){
            inflater.inflate(R.menu.activity_principal, menu);
            inflater.inflate(R.menu.activity_usuarios, menu);
        } else {
            inflater.inflate(R.menu.activity_usuarios, menu);
        }
        //getMenuInflater().inflate(R.menu.activity_principal, menu);
        return true;

    }


    private void buscarUsuario (String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        profileName.setText("Bienvenido "+jsonObject.getString("u_nombre")+" "+jsonObject.getString("u_apellido"));
                        //TxtUsuario.setText("Bienvenido "+jsonObject.getString("u_nombre")+" "+jsonObject.getString("u_apellido"));
                        //TxtUsuario.setText(preferences.getString("usuario",""));
                        String nombre = jsonObject.getString("u_nombre");
                        String apellido = jsonObject.getString("u_apellido");
                        Usuario = jsonObject.getString("u_usuario");
                        Toast.makeText(getApplicationContext(),"Hola " + nombre + " "+apellido,Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error de Conexión",Toast.LENGTH_SHORT).show();

            }
        }
        );

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}