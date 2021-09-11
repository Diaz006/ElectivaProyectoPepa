package com.my.first.taller_sharedpreference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminUsuarios extends AppCompatActivity {
    EditText edtid, edtnombre, edtapellido, edttelefono, edtusuario, edtemail, edtpasswordr, edtFechaNacimiento;
    Button btnActualizar, btnBuscar, btnEliminar, btncancerlar;

    RadioButton rbtHombre, rbtMujer, rbtnOtro;
    Boolean Bandera=true;

    Spinner spinerCiudad;


    String mSpinnerLabel;
    String Sexo;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuarios);
        id_formulario();
        cancelar();
        Spinner spinner = (Spinner) findViewById(R.id.spCiudad);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.ciudades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerLabel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarUsuario("http://192.168.100.7/ProyectoAndroid2/buscarusuario.php?codigo="+edtid.getText()+"");
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*base.editarUsuarios(Integer.parseInt(edtid.getText().toString()), edtnombre.getText().toString(), edtapellido.getText().toString(), Sexo, edtusuario.getText().toString(), edttelefono.getText().toString(), edtemail.getText().toString(), spinerCiudad.getSelectedItem().toString(), edtFechaNacimiento.getText().toString(), edtpasswordr.getText().toString());
                Toast.makeText(getApplicationContext(),"SE ACTUALIZO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                limpiar();*/
                ejecutarServicio("http://192.168.100.7/ProyectoAndroid2/editar_usuario.php");
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*base.eliminarUsuario(edtid.getText().toString());
                Toast.makeText(getApplicationContext(),"SE ELIMINO CORRECTAMENTE",Toast.LENGTH_SHORT).show();*/
                eliminarUsuario("http://192.168.100.7/ProyectoAndroid2/eliminar_usuario.php");
            }
        });
        setDayNight();

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

    /*------------------------------------------------------*/

    private void buscarUsuario (String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        edtnombre.setText(jsonObject.getString("u_nombre"));
                        edtapellido.setText(jsonObject.getString("u_apellido"));
                        switch (jsonObject.getString("u_sexo")) {
                                case "Masculino":
                                    rbtHombre.setChecked(Bandera);
                                    Sexo="Masculino";
                                    break;
                                case "Femenino":
                                    rbtMujer.setChecked(Bandera);
                                    Sexo="Femenino";
                                    break;
                                case "Otro":
                                    rbtnOtro.setChecked(Bandera);
                                    Sexo="Otro";
                                    break;
                                default:
                                    break;
                            }

                        edtusuario.setText(jsonObject.getString("u_usuario"));
                        edttelefono.setText(jsonObject.getString("u_telefono"));

                        //Setear spinner ciudad
                        String [] ciudades = getResources().getStringArray(R.array.ciudades);
                        for (int ia=0;ia<ciudades.length; ia++ ){
                            if(ciudades[ia].equals(jsonObject.getString("u_ciudad"))){
                                spinerCiudad.setSelection(ia);
                                break;
                            }
                        }

                        edtFechaNacimiento.setText(jsonObject.getString("u_fechaNacimiento"));
                        edtemail.setText(jsonObject.getString("u_email"));
                        edtpasswordr.setText(jsonObject.getString("u_password"));



                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),R.string.Activity_IDIncorrecto,Toast.LENGTH_SHORT).show();

            }
        }
        );

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view ).isChecked();
        switch (view.getId()){
            case R.id.rbtnHombre:
                if (checked){
                    Toast.makeText(AdminUsuarios.this,R.string.Activity_SexoMasculino,Toast.LENGTH_SHORT).show();
                    Sexo = "Masculino";
                }
                break;
            case R.id.rbtnFemenino:
                if (checked){
                    Toast.makeText(AdminUsuarios.this,R.string.Activity_SexoFemenino,Toast.LENGTH_SHORT).show();
                    Sexo = "Femenino";
                }
                break;
            case R.id.rbtnOtro:
                if (checked){
                    Toast.makeText(AdminUsuarios.this,R.string.Activity_SexoOtro,Toast.LENGTH_SHORT).show();
                    Sexo = "Otro";
                }
                break;
        }
    }

    public void id_formulario(){
        edtid = findViewById(R.id.edtID);
        edtnombre = findViewById(R.id.edtNombreAD);
        edtapellido = findViewById(R.id.edtApellidoAD);
        edtusuario = findViewById(R.id.edtUsuarioAD);
        edttelefono = findViewById(R.id.edtTelefonoAD);
        edtemail = findViewById(R.id.edtEmailAD);
        edtpasswordr = findViewById(R.id.edtPasswordAD);
        edtFechaNacimiento = findViewById(R.id.edtDateAD);


        rbtHombre = findViewById(R.id.rbtnHombre);
        rbtMujer = findViewById(R.id.rbtnFemenino);
        rbtnOtro = findViewById(R.id.rbtnOtro);
        spinerCiudad = findViewById(R.id.spCiudad);


        btncancerlar =findViewById(R.id.btnCancelarAD);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnBuscar = findViewById(R.id.btnBuscar);

    }

    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), R.string.Activity_UsuarioActualizado, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("codigo",edtid.getText().toString());
                parametros.put("nombre",edtnombre.getText().toString());
                parametros.put("apellido",edtapellido.getText().toString());
                parametros.put("sexo",Sexo);
                parametros.put("telefono",edttelefono.getText().toString());
                parametros.put("email",edtemail.getText().toString());
                parametros.put("ciudad",spinerCiudad.getSelectedItem().toString());
                parametros.put("fecha",edtFechaNacimiento.getText().toString());
                parametros.put("password",edtpasswordr.getText().toString());
                parametros.put("usuario",edtusuario.getText().toString());
                return parametros;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void limpiar(){
        edtid.setText("");
        edtnombre.setText("");
        edtapellido.setText("");
        edtusuario.setText("");
        edttelefono.setText("");
        edtemail.setText("");
        edtpasswordr.setText("");
        edtFechaNacimiento.setText("");

    }

    private void eliminarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), R.string.Activity_UsuarioEliminado, Toast.LENGTH_SHORT).show();
                limpiar();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("codigo",edtid.getText().toString());
                return parametros;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void cancelar () {
        btncancerlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminUsuarios.this,R.string.Activity_SalidaAdUsuario,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
            }
        });
    }

    public void showDatePickerDialog(View v) {
        final TextView etPlannedDate = (TextView) findViewById(R.id.edtDateAD);

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                //+1 porque enero es 0
                final String selectedDate = day + "/" + (month + 1) + "/" + year;
                etPlannedDate.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "Calendario");
    }

}
