package com.my.first.taller_sharedpreference;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegistroUsuarioActivity extends AppCompatActivity {
    EditText edtname, edtlastname, edttelefono, edtusuario, edtemail, edtpasswordr, edtFechaNacimiento;
    Button btnregistrar, btncancerlar;
    ImageButton imgContacto;
    Spinner spinerCiudad;


    String mSpinnerLabel;
    String Sexo;

    RequestQueue requestQueue;


    static final int PICK_CONTACT_REQUEST=1;


    private final static String CHANNEL_ID="NOTIFICACION";
    private final static int NOTIFICACION_ID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        edtname = findViewById(R.id.edtName);
        edtlastname = findViewById(R.id.edtLastName);
        id_formulario();
        cancelar();
        registrar();

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

        imgContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarContacto();
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

    private void seleccionarContacto() {
        Intent seleccContactoIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        seleccContactoIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); //Le decimos que vamos a seleccionar contactos
        //seleccContactoIntent.setType(ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        //seleccContactoIntent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
        startActivityForResult(seleccContactoIntent,PICK_CONTACT_REQUEST);
    } //Extraer Contactos

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PICK_CONTACT_REQUEST){
            if (resultCode==RESULT_OK){
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);

                if (cursor.moveToFirst()){
                    int columnaNombre = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME); // Nombre del contacto
                    String nombrec = cursor.getString(columnaNombre);
                    edtname.setText(nombrec);

                    int columnaTelefono = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER); // NUMERO del contacto
                    String Telefonoc = cursor.getString(columnaTelefono);
                    edttelefono.setText(Telefonoc);

                }
            }
        }
    } //Extraer Contactos

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view ).isChecked();
        switch (view.getId()){
            case R.id.rbtnHombre:
                if (checked){
                    Toast.makeText(RegistroUsuarioActivity.this,R.string.Activity_SexoMasculino,Toast.LENGTH_SHORT).show();
                    Sexo = "Masculino";
                }
                break;
            case R.id.rbtnFemenino:
                if (checked){
                    Toast.makeText(RegistroUsuarioActivity.this,R.string.Activity_SexoFemenino,Toast.LENGTH_SHORT).show();
                    Sexo = "Femenino";
                }
                break;
            case R.id.rbtnOtro:
                if (checked){
                    Toast.makeText(RegistroUsuarioActivity.this,R.string.Activity_SexoOtro,Toast.LENGTH_SHORT).show();
                    Sexo = "Otro";
                }
                break;
        }
    }


    public void id_formulario(){


        edtusuario = findViewById(R.id.edtUsuarioAD);
        edttelefono = findViewById(R.id.edtTelefonoAD);
        edtemail = findViewById(R.id.edtEmailAD);
        edtpasswordr = findViewById(R.id.edtPasswordAD);
        edtFechaNacimiento = findViewById(R.id.edtDateAD);

        spinerCiudad = findViewById(R.id.spCiudad);

        btnregistrar = findViewById(R.id.btnRegistrarse);
        btncancerlar = findViewById(R.id.btnCancelarAD);
        imgContacto =findViewById(R.id.btncontacto);


    }

    public void limpiar(){
        edtname.setText("");
        edtlastname.setText("");
        edtusuario.setText("");
        edttelefono.setText("");
        edtemail.setText("");
        edtpasswordr.setText("");
        edtFechaNacimiento.setText("");

    }

    public void registrar (){
        //final BaseSQLITE base = new BaseSQLITE(getApplicationContext());
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //base.agregarUsuario(edtnombre.getText().toString(),edtapellido.getText().toString(),Sexo,edtusuario.getText().toString(),edttelefono.getText().toString(),edtemail.getText().toString(),spinerCiudad.getSelectedItem().toString(),edtFechaNacimiento.getText().toString(),edtpasswordr.getText().toString());
                ejecutarServicio("http://192.168.100.7/ProyectoAndroid2/insertar_Usuario.php");
                //Snackbar.make(v, "Usuario Agregado Bien", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                //limpiar();
                createNotificationChannel();
                crearNotificacion();
                finish();

            }
        });

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
        builder.setSmallIcon(R.drawable.bgwcorronaapp);
        builder.setContentTitle("Registro de Nuevo Usuario");
        builder.setContentText("Usuario: " + edtname.getText().toString() + " "+edtlastname.getText().toString() + " Creado con Exito");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);//Para poner luz en el celular se prueba en telefonos reales
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});//Para que bibre
        builder.setDefaults(Notification.DEFAULT_SOUND); //Para anadir un sonido

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());



    }

    private void ejecutarServicio(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), R.string.Activity_UsuarioAgregado, Toast.LENGTH_LONG).show();
                limpiar();
                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();

                parametros.put("nombre",edtname.getText().toString());
                parametros.put("apellido",edtlastname.getText().toString());
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

    public void cancelar () {
        btncancerlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegistroUsuarioActivity.this,R.string.Activity_SalidaNuevoUsuario,Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                //Snackbar.make(v, "Saliendo de Nuevo Usuario", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
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

