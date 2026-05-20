package com.example.cineyseriesjavieralfonsoaguiar2.datos.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Genero;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Usuario;

import java.util.List;

@Dao
public interface DaoApp {
    @Insert
    void insertarUsuario(Usuario usuario);

    @Insert
    long insertarPelicula(Pelicula pelicula);

    @Insert
    void insertarGenero(Genero genero);

    // busca en la base de datos si hay un usuario con correo y ctra que coincide
    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    Usuario iniciarSesion(String correo, String contrasena);

    // para mandar el sms cogemos el tlf del admin
    @Query("SELECT telefono FROM usuarios WHERE esAdmin = 1 LIMIT 1")
    String obtenerTelefonoAdmin();

    //obtenemos todos los usuarios registrados para poder mostrarlos
    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodosUsuarios();

    @Query("SELECT * FROM peliculas ORDER BY titulo")
    List<Pelicula> obtenerTodasPeliculas();

    @Query("SELECT * FROM peliculas WHERE tipo = :tipo ORDER BY titulo")
    List<Pelicula> obtenerPeliculasPorTipo(String tipo);

    @Query("SELECT COUNT(*) FROM peliculas")
    int contarPeliculas();

    @Query("SELECT COUNT(*) FROM generos")
    int contarGeneros();

    // para que el admin pueda eliminar el usuario que desee
    @Delete
    void eliminarUsuario(Usuario usuario);

    @Delete
    void eliminarPelicula(Pelicula pelicula);

    @Update
    void actualizarPelicula(Pelicula pelicula);
}

