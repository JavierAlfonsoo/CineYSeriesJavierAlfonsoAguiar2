package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {
    @Insert
    void insertUser(Usuario user);

    @Insert
    long insertPelicula(Pelicula pelicula);

    @Insert
    void insertGenero(Genero genero);

    // busca en la base de datos si hay un usuario con correo y ctra que coincide
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND password = :password LIMIT 1")
    Usuario login(String correo, String password);

    // para mandar el sms cogemos el tlf del admin
    @Query("SELECT telefono FROM usuarios WHERE esAdmin = 1 LIMIT 1")
    String getAdminPhone();

    //obtenemos todos los usuarios registrados para poder mostrarlos
    @Query("SELECT * FROM usuarios")
    List<Usuario> getAllUsers();

    @Query("SELECT * FROM peliculas ORDER BY titulo")
    List<Pelicula> getAllPeliculas();

    @Query("SELECT * FROM peliculas WHERE tipo = :tipo ORDER BY titulo")
    List<Pelicula> getPeliculasByTipo(String tipo);

    @Query("SELECT COUNT(*) FROM peliculas")
    int countPeliculas();

    @Query("SELECT COUNT(*) FROM generos")
    int countGeneros();

    // para que el admin pueda eliminar el usuario que desee
    @Delete
    void deleteUser(Usuario user);

    @Delete
    void deletePelicula(Pelicula pelicula);

    @Update
    void updatePelicula(Pelicula pelicula);
}
