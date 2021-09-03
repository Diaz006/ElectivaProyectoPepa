package com.my.first.taller_sharedpreference;

public class UsuariosModelo {
    private Integer id;
    private String nombre, apellido, sexo, usuario, telefono, ciudad, fecha, email, paswword;

    public UsuariosModelo() {
    }

    public UsuariosModelo(Integer id, String nombre, String apellido, String sexo, String usuario, String telefono, String ciudad, String fecha, String email, String paswword) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.usuario = usuario;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.fecha = fecha;
        this.email = email;
        this.paswword = paswword;
    }

    public UsuariosModelo(String nombre, String telefono, String email) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaswword() {
        return paswword;
    }

    public void setPaswword(String paswword) {
        this.paswword = paswword;
    }
}
