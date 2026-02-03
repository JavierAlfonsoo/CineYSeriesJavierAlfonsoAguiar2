package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "generos")
public class Genero {
    @PrimaryKey
    public int id;
    public String nombre;
}