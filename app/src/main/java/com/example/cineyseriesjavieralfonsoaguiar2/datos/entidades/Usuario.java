package com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario{
    @PrimaryKey(autoGenerate = true)
    public int idUsuario;

    public String nombre;
    public String correo;
    public String contrasena;
    //Para el sms
    public String telefono;
    //Para crear distintas opciones dependiendo del usuario
    public boolean esAdmin;

    public Usuario() {}

    public Usuario(String nombre, String correo, String contrasena, String telefono, boolean esAdmin) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.esAdmin = esAdmin;
    }
}
