package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas.AdaptadorPelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.BaseDatosApp;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.admin.ActividadAdmin;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.mapas.ActividadMapa;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas.FragmentoDialogoConfirmarEliminacion;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas.ActividadNuevaPelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas.ActividadRecomendaciones;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.servicio.ServicioSmsBateria;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.ajustes.ActividadPreferencias;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ActividadPrincipal extends AppCompatActivity implements FragmentoDialogoConfirmarEliminacion.EscuchadorConfirmarEliminacion {

    private static final int CODIGO_NUEVA_PELICULA = 10;

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private RecyclerView recyclerPelis;
    private ArrayList<Pelicula> pelisLista;
    private AdaptadorPelicula adaptador;
    private BaseDatosApp baseDatos;
    private Pelicula peliculaPendienteBorrar;

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        aplicarModoOscuro();
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_main);

        baseDatos = BaseDatosApp.obtenerInstancia(this);
        recyclerPelis = findViewById(R.id.rvPelisSeries);
        Button botonAdmin = findViewById(R.id.btnAdminPanel);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        recyclerPelis.setLayoutManager(new LinearLayoutManager(this));
        pelisLista = new ArrayList<>();
        adaptador = new AdaptadorPelicula(this, pelisLista, new AdaptadorPelicula.EscuchadorAccionPelicula() {
            @Override
            public void alEliminar(Pelicula pelicula) {
                peliculaPendienteBorrar = pelicula;
                FragmentoDialogoConfirmarEliminacion.nuevaInstancia(pelicula.titulo)
                        .show(getSupportFragmentManager(), "confirm_delete");
            }

            @Override
            public void alFavorito(Pelicula pelicula) {
                pelicula.favorito = !pelicula.favorito;
                baseDatos.daoApp().actualizarPelicula(pelicula);
                cargarPeliculas();
            }
        });
        recyclerPelis.setAdapter(adaptador);

        boolean esAdmin = getIntent().getBooleanExtra("IS_ADMIN", false);
        if (esAdmin) {
            botonAdmin.setVisibility(View.VISIBLE);
            botonAdmin.setOnClickListener(v -> startActivity(new Intent(this, ActividadAdmin.class)));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.titulo);

        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_recomendaciones) {
                startActivity(new Intent(this, ActividadRecomendaciones.class));
            } else if (id == R.id.nav_ayuda) {
                startActivity(new Intent(this, ActividadAyuda.class));
            } else if (id == R.id.nav_acerca) {
                startActivity(new Intent(this, ActividadAcercaDe.class));
            } else if (id == R.id.nav_mapa) {
                startActivity(new Intent(this, ActividadMapa.class));
            } else if (id == R.id.nav_volver) {
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        fab.setOnClickListener(v -> {
            Intent intencion = new Intent(this, ActividadNuevaPelicula.class);
            startActivityForResult(intencion, CODIGO_NUEVA_PELICULA);
        });

        cargarPeliculas();
        iniciarServicioBateria();
    }

    private void cargarPeliculas() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        String filtro = preferencias.getString("ordenar_por", "todos");
        pelisLista.clear();
        if ("pelicula".equals(filtro) || "serie".equals(filtro)) {
            String tipo = "pelicula".equals(filtro) ? "Película" : "Serie";
            pelisLista.addAll(baseDatos.daoApp().obtenerPeliculasPorTipo(tipo));
        } else {
            pelisLista.addAll(baseDatos.daoApp().obtenerTodasPeliculas());
        }
        adaptador.notifyDataSetChanged();
    }

    private void iniciarServicioBateria() {
        Intent intencion = new Intent(this, ServicioSmsBateria.class);
        startService(intencion);
    }

    private void aplicarModoOscuro() {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        boolean modoOscuro = preferencias.getBoolean("modo_oscuro", false);
        AppCompatDelegate.setDefaultNightMode(modoOscuro
                ? AppCompatDelegate.MODE_NIGHT_YES
                : AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarModoOscuro();
        cargarPeliculas();
    }

    @Override
    protected void onActivityResult(int codigoSolicitud, int codigoResultado, Intent datos) {
        super.onActivityResult(codigoSolicitud, codigoResultado, datos);
        if (codigoSolicitud == CODIGO_NUEVA_PELICULA && codigoResultado == RESULT_OK) {
            cargarPeliculas();
            Toast.makeText(this, R.string.pelicula_guardada, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void alConfirmarEliminacion() {
        if (peliculaPendienteBorrar != null) {
            baseDatos.daoApp().eliminarPelicula(peliculaPendienteBorrar);
            Toast.makeText(this, getString(R.string.pelicula_eliminada, peliculaPendienteBorrar.titulo),
                    Toast.LENGTH_SHORT).show();
            peliculaPendienteBorrar = null;
            cargarPeliculas();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ActividadPreferencias.class));
            return true;
        }
        if (id == R.id.action_audio) {
            startActivity(new Intent(this, ActividadGuiaAudio.class));
            return true;
        }
        if (id == R.id.opcion_salir) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

