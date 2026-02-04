package com.example.cineyseriesjavieralfonsoaguiar2.ui;

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

import com.example.cineyseriesjavieralfonsoaguiar2.PeliculaAdapter;
import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private RecyclerView recyclerPelis;
    ArrayList<Pelicula> pelisLista;
    private FloatingActionButton fab;
    private Button botonAdmin;

    // Constante para el código de solicitud

    private PeliculaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aplicarModoOscuro();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPelis = findViewById(R.id.rvPelisSeries);
        botonAdmin = findViewById(R.id.btnAdminPanel);
        recyclerPelis.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.floatingActionButton);

        pelisLista = new ArrayList<Pelicula>();

        boolean esAdmin = getIntent().getBooleanExtra("IS_ADMIN", false);
        if (esAdmin) {
            // si el usuario cumple con esadmin le mostramos el boton
            botonAdmin.setVisibility(View.VISIBLE);

            botonAdmin.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, AdminActivity.class));
            });
        }

        // Añadimos peliculas de prueba al recycler
        pelisLista.add(new Pelicula(R.drawable.snatch, "Guy Ritchie", "Snatch: cerdos y diamantes", "Jason Statan",
                "Alan Ford", "Comedia", "04/11/2000", 99, true, true, 10));
        pelisLista.add(new Pelicula(R.drawable.eljuego, "David Fincher", "El juego", "Michael Douglas", "Sean Penn",
                "Suspense", "12/9/1997", 128, false, false, 9));
        adapter = new PeliculaAdapter(this, pelisLista);
        recyclerPelis.setAdapter(adapter);

        // Pongo titulo a la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.titulo);

        // Asigno el drawerLayout y el navigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);

        // Configuro el boton de las tres lineas
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Gestiono la navegación lateral
        navView.setNavigationItemSelectedListener(item -> {
            final int id = item.getItemId();
            if (id == R.id.nav_recomendaciones) {
                Toast.makeText(this, "Seleccionaste recomendaciones", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_ayuda) {
                Intent intent = new Intent(this, AyudaActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_acerca) {
                Intent intent = new Intent(this, AcercaDeActivity.class);
                startActivity(intent);
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Configuracion crear nueva pelicula
        //fab.setOnClickListener(v -> {
        //    Intent intent = new Intent(this, NuevaPeliculaActivity.class);
        //    startActivityForResult(intent, CODIGO_NUEVA_PELICULA);
        //});
    }

    private void aplicarModoOscuro() {

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);

        boolean darkMode = prefs.getBoolean("pref_dark_mode", false);

        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
            );
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
            );
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        aplicarModoOscuro();
        adapter.notifyDataSetChanged();
    }

    // inflater para el menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // AQUÍ ABRIMOS LA ACTIVIDAD QUE ACABAMOS DE CREAR
            Intent intent = new Intent(this, ActivityPreferencias.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}