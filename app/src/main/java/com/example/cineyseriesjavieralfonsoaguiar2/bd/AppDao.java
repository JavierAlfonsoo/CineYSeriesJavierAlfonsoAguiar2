package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    void insertUser(Usuario user);

    // busca en la base de datos si hay un usuario con correo y ctra que coincide
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND password = :password LIMIT 1")
    Usuario login(String correo, String password);

    // para mandar el sms cogemos el tlf del admin
    @Query("SELECT telefono FROM usuarios WHERE esAdmin = 1 LIMIT 1")
    String getAdminPhone();

    //obtenemos todos los usuarios registrados para poder mostrarlos
    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsers();

    // para que el admin pueda eliminar el usuario que desee
    @Delete
    void deleteUser(Usuario user);
}