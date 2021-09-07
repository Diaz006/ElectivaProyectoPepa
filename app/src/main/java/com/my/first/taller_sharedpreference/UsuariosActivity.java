package com.my.first.taller_sharedpreference;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewusuario;
    private UsuariosAdaptador adaptadorUsuario;
    Button btnsalir;
    RequestQueue requestQueue;
    List<UsuariosModelo> usuariosa = new ArrayList<>();
    String URL;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        //List<UsuariosModelo> usuarios = new ArrayList<>();

        recyclerViewusuario=(RecyclerView)findViewById(R.id.recycleUsuarios);
        recyclerViewusuario.setLayoutManager(new LinearLayoutManager(this));

        //BaseSQLITE base = new BaseSQLITE(getApplicationContext());
        //usuarios.add(new UsuariosModelo(jsonObject.getString("u_nombre"),jsonObject.getString("u_telefono"),jsonObject.getString("u_email")));
        //usuarios.add(new UsuariosModelo("hola","0896756","bryan@gmail.com"));
        //usuarios = MostrarUsuario("http://192.168.100.7/ProyectoAndroid2/mostrar_usuarios.php");
        //Toast.makeText(getApplicationContext(),usuarios.get(0).getNombre(), Toast.LENGTH_SHORT).show();
        URL="http://192.168.100.7/ProyectoAndroid2/mostrar_usuarios.php";
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int ia=0;
                JSONObject jsonObject = null;
                do {
                    try {
                        jsonObject = response.getJSONObject(ia);
                        usuariosa.add(new UsuariosModelo(jsonObject.getString("u_nombre"),jsonObject.getString("u_telefono"),jsonObject.getString("u_email")));
                        //usuariosa.add(new UsuariosModelo("holvbcvba","089675346","bryan@gmail.com"));
                        //Toast.makeText(getApplicationContext(),usuariosa.get(ia).getNombre(), Toast.LENGTH_SHORT).show();
                        adaptadorUsuario = new UsuariosAdaptador(usuariosa);
                        recyclerViewusuario.setAdapter(adaptadorUsuario);
                        //Toast.makeText(getApplicationContext(),jsonObject.getString("u_nombre"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    ia++;
                }while (ia<response.length());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"NO SE ENCONTRARON USUARIOS",Toast.LENGTH_SHORT).show();

            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        //Toast.makeText(getApplicationContext(),usuariosa.get(1).getNombre(), Toast.LENGTH_SHORT).show();
        //return usuariosa;


        //Toast.makeText(getApplicationContext(),usuariosa.toString(), Toast.LENGTH_SHORT).show();
        //adaptadorUsuario = new UsuariosAdaptador(usuariosa);
        //recyclerViewusuario.setAdapter(adaptadorUsuario);

        btnsalir = findViewById(R.id.btnSalir);
        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void MostrarUsuario (String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int ia=0;
                JSONObject jsonObject = null;
                do {
                    try {
                        jsonObject = response.getJSONObject(ia);
                        usuariosa.add(new UsuariosModelo(jsonObject.getString("u_nombre"),jsonObject.getString("u_telefono"),jsonObject.getString("u_email")));
                        //usuariosa.add(new UsuariosModelo("holvbcvba","089675346","bryan@gmail.com"));
                        Toast.makeText(getApplicationContext(),usuariosa.get(ia).getNombre(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(getApplicationContext(),jsonObject.getString("u_nombre"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    ia++;
                }while (ia<response.length());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"NO SE ENCONTRARON USUARIOS",Toast.LENGTH_SHORT).show();

            }
        }
        );

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        //Toast.makeText(getApplicationContext(),usuariosa.get(1).getNombre(), Toast.LENGTH_SHORT).show();
        //return usuariosa;
    }

}