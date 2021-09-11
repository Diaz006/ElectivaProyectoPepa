package com.my.first.taller_sharedpreference;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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



    private final static String CHANNEL_ID="NOTIFICACION";
    private final static int NOTIFICACION_ID=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        //preferences.edit().clear().commit();

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
                //preferences.edit().clear().commit();

                //Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intent);
                EjemploAsyncTask ejemploAsyncTask = new EjemploAsyncTask();
                ejemploAsyncTask.execute();

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
        setDayNight();

    }

    private void UnSegundo (){
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
    }

    private class EjemploAsyncTask extends AsyncTask<Void,Integer,Boolean> {
        @Override //1 Funcion que se ejecuta en el hilo principal todas las cosas que se quieren ejecutar antes de que vayan a segundo plano
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setMax(100);
            //progressBar.setProgress(0);
        }

        @Override //4. Ultima funcion se acaba los procesos en el hilo aqui presentas un ultima accion
        protected void onPostExecute(Boolean unused) {
            //super.onPostExecute(unused);
            if (unused){
                createNotificationChannel();
                crearNotificacion();
                //Toast.makeText(getBaseContext(),"Tarea Larga finalizada en Asynktask", Toast.LENGTH_SHORT).show();
            }
        }

        @Override //3. Se ejecuta el hilo de la interfaz de usuario
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //progressBar.setProgress(values[0].intValue());

        }

        @Override //Si cortas la ejecucion del segundo hilo aqui llamas a esa función
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(getBaseContext(),"Tarea Larga ha sido cancelada", Toast.LENGTH_SHORT).show();
        }

        @Override // 2 Funcion que hay que hacer en segundo plano, recibe como parametro de entradas para ejecutar las instrucciones y dar el segundo plano
        protected Boolean doInBackground(Void... voids) {
            for (int i=1; i<10;i++){
                UnSegundo();
                //publishProgress(i*12);
                if (isCancelled()) {
                    break;
                }
            }
            return true;
        }
    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notication";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    private void crearNotificacion(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle("Nueva Noticia");
        builder.setContentText("El COVID 19 Ha Provocado un Cambio en la Vida Diaria");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);//Para poner luz en el celular se prueba en telefonos reales
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});//Para que bibre
        builder.setDefaults(Notification.DEFAULT_SOUND); //Para anadir un sonido

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());



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
                Toast.makeText(this, R.string.Activity_adUsuario, Toast.LENGTH_SHORT).show();
                startActivity(adusuario);
                return true;

            case R.id.mos_usuarios:
                Intent mostrarUsuarios = new Intent(getApplicationContext(), UsuariosActivity.class);
                Toast.makeText(this, R.string.Activity_listaUsuario, Toast.LENGTH_SHORT).show();
                startActivity(mostrarUsuarios);
                return true;

            case R.id.menu_usuariosa:
                Intent adusuarioa = new Intent(getApplicationContext(), ConfiguracionActivity.class);
                Toast.makeText(this, R.string.Activity_confiUsuario, Toast.LENGTH_SHORT).show();
                startActivity(adusuarioa);
                finish();
                return true;

            case R.id.cerrarsesion:
                SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
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
                        profileName.setText(getString(R.string.Activity_BIENVENIDO)+" "+jsonObject.getString("u_nombre")+" "+jsonObject.getString("u_apellido"));
                        //TxtUsuario.setText("Bienvenido "+jsonObject.getString("u_nombre")+" "+jsonObject.getString("u_apellido"));
                        //TxtUsuario.setText(preferences.getString("usuario",""));
                        String nombre = jsonObject.getString("u_nombre");
                        String apellido = jsonObject.getString("u_apellido");
                        Usuario = jsonObject.getString("u_usuario");
                        Toast.makeText(getApplicationContext(),getString(R.string.Activity_SALUDO) +" "+ nombre + " "+apellido,Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),R.string.Activity_ErrorConexion,Toast.LENGTH_SHORT).show();

            }
        }
        );

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}