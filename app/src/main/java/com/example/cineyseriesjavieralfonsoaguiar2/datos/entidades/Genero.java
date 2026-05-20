package com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "generos")
public class Genero {
    @PrimaryKey
    public int id;
    public String nombre;

    public Genero() {
    }

    public Genero(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}

