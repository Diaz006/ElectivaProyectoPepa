package com.my.first.taller_sharedpreference;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edtusuario, edtpassword;
    Button btnIniciarSesion;
    Button btnNuevoUsuario;
    //CheckBox checkGuardarSesion;

    String usuario, password;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String llave="Sesion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarElementos();
        recuperarPreferenciaas();
        /*
        if (resvisarSesion()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
            alerta.setMessage("Desea continuar con la Sesión Guarda?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //startActivity(new Intent(this,ActivityPrincipal.class));
                            edtusuario.setText(preferences.getString("usuario",""));
                            edtpassword.setText(preferences.getString("password",""));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Continuar Sesión");
            titulo.show();

        }else{
            String mensaje = "Iniciar Sesion";
            Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
        }*/
        /*
        final BaseSQLITE base = new BaseSQLITE(getApplicationContext());
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtusuario.getText().toString().equals("admin") && edtpassword.getText().toString().equals("admin")){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setMessage("Desea Guardar la Sesión?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Bienvenido Usuario: "+edtusuario.getText().toString(),Toast.LENGTH_SHORT).show();
                                    guardarSesion(true);
                                    Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
                                    intent.putExtra("usuario",edtusuario.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "Bienvenido Usuario: "+edtusuario.getText().toString(),Toast.LENGTH_SHORT).show();
                                    editor.putBoolean(llave,false);
                                    Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
                                    intent.putExtra("usuario",edtusuario.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Guardar Sesión");
                    titulo.show();
                }else{
                    Toast.makeText(MainActivity.this,"Usuario Incorrecto",Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario=edtusuario.getText().toString();
                password=edtpassword.getText().toString();
                if (!usuario.isEmpty() && !password.isEmpty()){
                    validarUsuario("http://192.168.100.7/ProyectoAndroid2/validar_usuario.php"); //aqui ponle tu url de la carpeta htdoct Wilson
                   //Intent intent=new Intent(getApplicationContext(),ActivityPrincipal.class);
                    //startActivity(intent);
                    //finish();
                }else{
                    Toast.makeText(MainActivity.this,"Campo usuario o contrasena vacio",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getApplicationContext(),RegistroUsuarioActivity.class);
                startActivity(intento);
                //finish();
            }
        });


    }

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    guardarPreferencias();
                    Intent intent=new Intent(getApplicationContext(),ActivityPrincipal.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MainActivity.this,"Usuario o Contrasena Incorrecta",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,""+error,Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();

                parametros.put("usuario",usuario);
                parametros.put("password",password);
                return parametros;
            }
        };

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void guardarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("password",password);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    private void recuperarPreferenciaas(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        edtusuario.setText(preferences.getString("usuario",""));
        edtpassword.setText(preferences.getString("password",""));

    }

    private void inicializarElementos(){

        preferences = getSharedPreferences("sesiones",Context.MODE_PRIVATE);
        editor = preferences.edit();

        edtusuario = findViewById(R.id.edtUsuario);
        edtpassword =findViewById(R.id.edtPassword);
        btnIniciarSesion = findViewById(R.id.btnLogin);
        btnNuevoUsuario = findViewById(R.id.btnRegistrarse);

    }

    /*
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPopup (View v){
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.activity_principal, popup.getMenu());
        popup.show();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_principal, menu);
        //getMenuInflater().inflate(R.menu.menu_login,menu);
        return true;
    }*/


}