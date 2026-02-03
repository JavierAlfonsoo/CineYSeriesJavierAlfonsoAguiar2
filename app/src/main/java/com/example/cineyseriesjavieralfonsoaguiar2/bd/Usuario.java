package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario{
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String nombre;
    public String correo;
    public String password;
    //Para el sms
    public String telefono;
    //Para crear distintas opciones dependiendo del usuario
    public boolean esAdmin;

    public Usuario() {}

    public Usuario(String nombre, String correo, String password, String telefono, boolean esAdmin) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.telefono = telefono;
        this.esAdmin = esAdmin;
    }
}