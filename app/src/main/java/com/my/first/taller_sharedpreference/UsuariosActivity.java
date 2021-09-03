package com.my.first.taller_sharedpreference;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UsuariosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewusuario;
    private UsuariosAdaptador adaptadorUsuario;
    Button btnsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        recyclerViewusuario=(RecyclerView)findViewById(R.id.recycleUsuarios);
        recyclerViewusuario.setLayoutManager(new LinearLayoutManager(this));

        BaseSQLITE base = new BaseSQLITE(getApplicationContext());

        adaptadorUsuario = new UsuariosAdaptador(base.mostrarUsuarios());
        recyclerViewusuario.setAdapter(adaptadorUsuario);

        btnsalir = findViewById(R.id.btnSalir);
        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}