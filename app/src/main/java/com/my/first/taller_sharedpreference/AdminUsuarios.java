package com.my.first.taller_sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AdminUsuarios extends AppCompatActivity {
    EditText edtid, edtnombre, edtapellido, edttelefono, edtusuario, edtemail, edtpasswordr, edtFechaNacimiento;
    Button btnActualizar, btnBuscar, btnEliminar, btncancerlar;

    RadioButton rbtHombre, rbtMujer, rbtnOtro;
    Boolean Bandera=true;

    Spinner spinerCiudad;


    String mSpinnerLabel;
    String Sexo;

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


        final BaseSQLITE base = new BaseSQLITE(getApplicationContext());
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuariosModelo usuarios = new UsuariosModelo();
                base.buscarUsuario(usuarios,edtid.getText().toString());
                Integer pr = usuarios.getId();
                Integer a = Integer.parseInt(edtid.getText().toString());

                if (pr==a){
                    edtnombre.setText(usuarios.getNombre());
                    edtapellido.setText(usuarios.getApellido());
                    //Setear sexo
                    switch (usuarios.getSexo()){
                        case "Masculino":
                            rbtHombre.setChecked(Bandera);
                            break;
                        case "Femenino":
                            rbtMujer.setChecked(Bandera);
                            break;
                        case "Otro":
                            rbtnOtro.setChecked(Bandera);
                            break;
                        default :
                            break;
                    }

                    edtusuario.setText(usuarios.getUsuario());
                    edttelefono.setText(usuarios.getTelefono());

                    //Setear spinner ciudad
                    String [] ciudades = getResources().getStringArray(R.array.ciudades);
                    for (int i=0;i<ciudades.length; i++ ){
                        if(ciudades[i].equals(usuarios.getCiudad())){
                            spinerCiudad.setSelection(i);
                            break;
                        }
                    }

                    edtFechaNacimiento.setText(usuarios.getFecha());
                    edtemail.setText(usuarios.getEmail());
                    edtpasswordr.setText(usuarios.getPaswword());
                }else{
                    Toast.makeText(getApplicationContext(),"ID no Valida",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base.editarUsuarios(Integer.parseInt(edtid.getText().toString()), edtnombre.getText().toString(), edtapellido.getText().toString(), Sexo, edtusuario.getText().toString(), edttelefono.getText().toString(), edtemail.getText().toString(), spinerCiudad.getSelectedItem().toString(), edtFechaNacimiento.getText().toString(), edtpasswordr.getText().toString());
                Toast.makeText(getApplicationContext(),"SE ACTUALIZO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                limpiar();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base.eliminarUsuario(edtid.getText().toString());
                Toast.makeText(getApplicationContext(),"SE ELIMINO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                limpiar();
            }
        });

    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view ).isChecked();
        switch (view.getId()){
            case R.id.rbtnHombre:
                if (checked){
                    Toast.makeText(AdminUsuarios.this,"Sexo Masculino",Toast.LENGTH_SHORT).show();
                    Sexo = "Masculino";
                }
                break;
            case R.id.rbtnFemenino:
                if (checked){
                    Toast.makeText(AdminUsuarios.this,"Sexo Femenino",Toast.LENGTH_SHORT).show();
                    Sexo = "Femenino";
                }
                break;
            case R.id.rbtnOtro:
                if (checked){
                    Toast.makeText(AdminUsuarios.this,"Sexo Otro",Toast.LENGTH_SHORT).show();
                    Sexo = "Otro";
                }
                break;
        }
    }


    public void id_formulario(){
        edtid = findViewById(R.id.edtID);
        edtnombre = findViewById(R.id.edtNombreAD);
        edtapellido = findViewById(R.id.edtApellidoAD);
        edtusuario = findViewById(R.id.edtUsuario);
        edttelefono = findViewById(R.id.edtTelefono);
        edtemail = findViewById(R.id.edtEmail);
        edtpasswordr = findViewById(R.id.edtPassword);
        edtFechaNacimiento = findViewById(R.id.edtDate);


        rbtHombre = findViewById(R.id.rbtnHombre);
        rbtMujer = findViewById(R.id.rbtnFemenino);
        rbtnOtro = findViewById(R.id.rbtnOtro);
        spinerCiudad = findViewById(R.id.spCiudad);


        btncancerlar =findViewById(R.id.btnCancelar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnBuscar = findViewById(R.id.btnBuscar);

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

    public void cancelar () {
        btncancerlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminUsuarios.this,"Salida de Administración de Usuario.....",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                finish();
            }
        });
    }


    public void showDatePickerDialog(View v) {
        final TextView etPlannedDate = (TextView) findViewById(R.id.edtDate);

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
