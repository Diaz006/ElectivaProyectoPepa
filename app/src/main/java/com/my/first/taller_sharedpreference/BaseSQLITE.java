package com.my.first.taller_sharedpreference;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BaseSQLITE extends SQLiteOpenHelper {
    private static final String NOMBRE_BD="UsuariosTaller.sqlite";
    private static final int VERSION_BD=1;
    private static final String TABLA_USUARIO="CREATE TABLE USUARIOS(id INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, APELLIDO TEXT, SEXO TEXT, USUARIO TEXT, TELEFONO TEXT, EMAIL TEXT, CIUDAD TEXT, FECHA TEXT, PASSWORD TEXT)";

    public BaseSQLITE(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLA_USUARIO);
        db.execSQL(TABLA_USUARIO);
    }

    public void agregarUsuario(String nombre, String apellido, String sexo, String usuario, String telefono, String email, String ciudad, String fecha, String password) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            bd.execSQL("INSERT INTO USUARIOS (NOMBRE, APELLIDO, SEXO, USUARIO, TELEFONO, EMAIL, CIUDAD, FECHA, PASSWORD) VALUES('" + nombre + "','" + apellido + "','" + sexo + "','" + usuario + "','" + telefono + "','" + email + "','" + ciudad + "','" + fecha + "','" + password + "')");
            bd.close();
        }
    }

    public List<UsuariosModelo> mostrarUsuarios(){
        SQLiteDatabase bd=getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM USUARIOS", null);
        List<UsuariosModelo> usuarios = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                usuarios.add(new UsuariosModelo(cursor.getString(0),cursor.getString(1),cursor.getString(6)));

            }while(cursor.moveToNext());
        }
        return usuarios;
    }

    public void buscarUsuario(UsuariosModelo usuario, String ID){
        SQLiteDatabase bd=getReadableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM USUARIOS WHERE id='"+ID+"'", null);
        if (cursor.moveToFirst()){
                do {
                    usuario.setId(Integer.parseInt(cursor.getString(0)));
                    usuario.setNombre(cursor.getString(1));
                    usuario.setApellido(cursor.getString(2));
                    usuario.setSexo(cursor.getString(3));
                    usuario.setUsuario(cursor.getString(4));
                    usuario.setTelefono(cursor.getString(5));
                    usuario.setEmail(cursor.getString(6));
                    usuario.setCiudad(cursor.getString(7));
                    usuario.setFecha(cursor.getString(8));
                    usuario.setPaswword(cursor.getString(9));

                }while(cursor.moveToNext());
        }
    }

    public void editarUsuarios(Integer id, String nombre, String apellido, String sexo, String usuario, String telefono, String email, String ciudad, String fecha, String password){
        SQLiteDatabase bd=getWritableDatabase();
        if (bd!=null){
            bd.execSQL("UPDATE USUARIOS SET NOMBRE='"+nombre+"', APELLIDO='"+apellido+"', SEXO='"+sexo+"', USUARIO='"+usuario+"', TELEFONO='"+telefono+"', EMAIL='"+email+"', CIUDAD='"+ciudad+"', FECHA='"+fecha+"', PASSWORD='"+password+"' WHERE id='"+id+"'");
            bd.close();
        }
    }

    public void eliminarUsuario(String codigo){
        SQLiteDatabase bd=getWritableDatabase();
        if (bd!=null){
            bd.execSQL("DELETE FROM USUARIOS WHERE id='"+codigo+"'");
            bd.close();
        }
    }

}
