package com.example.cineyseriesjavieralfonsoaguiar2.ui.main;

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

import com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas.PeliculaAdapter;
import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.data.AppDatabase;
import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.admin.AdminActivity;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.maps.MapaActivity;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas.ConfirmDeleteDialogFragment;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas.NuevaPeliculaActivity;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas.RecomendacionesActivity;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.service.BatterySmsService;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.settings.ActivityPreferencias;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ConfirmDeleteDialogFragment.ConfirmDeleteListener {

    private static final int CODIGO_NUEVA_PELICULA = 10;

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private RecyclerView recyclerPelis;
    private ArrayList<Pelicula> pelisLista;
    private PeliculaAdapter adapter;
    private AppDatabase db;
    private Pelicula peliculaPendienteBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aplicarModoOscuro();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        recyclerPelis = findViewById(R.id.rvPelisSeries);
        Button botonAdmin = findViewById(R.id.btnAdminPanel);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        recyclerPelis.setLayoutManager(new LinearLayoutManager(this));
        pelisLista = new ArrayList<>();
        adapter = new PeliculaAdapter(this, pelisLista, new PeliculaAdapter.OnPeliculaActionListener() {
            @Override
            public void onDelete(Pelicula pelicula) {
                peliculaPendienteBorrar = pelicula;
                ConfirmDeleteDialogFragment.newInstance(pelicula.titulo)
                        .show(getSupportFragmentManager(), "confirm_delete");
            }

            @Override
            public void onFavorite(Pelicula pelicula) {
                pelicula.favorito = !pelicula.favorito;
                db.appDao().updatePelicula(pelicula);
                cargarPeliculas();
            }
        });
        recyclerPelis.setAdapter(adapter);

        boolean esAdmin = getIntent().getBooleanExtra("IS_ADMIN", false);
        if (esAdmin) {
            botonAdmin.setVisibility(View.VISIBLE);
            botonAdmin.setOnClickListener(v -> startActivity(new Intent(this, AdminActivity.class)));
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
                startActivity(new Intent(this, RecomendacionesActivity.class));
            } else if (id == R.id.nav_ayuda) {
                startActivity(new Intent(this, AyudaActivity.class));
            } else if (id == R.id.nav_acerca) {
                startActivity(new Intent(this, AcercaDeActivity.class));
            } else if (id == R.id.nav_mapa) {
                startActivity(new Intent(this, MapaActivity.class));
            } else if (id == R.id.nav_volver) {
                finish();
            }
            drawerLayout.closeDrawers();
            return true;
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, NuevaPeliculaActivity.class);
            startActivityForResult(intent, CODIGO_NUEVA_PELICULA);
        });

        cargarPeliculas();
        iniciarServicioBateria();
    }

    private void cargarPeliculas() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String filtro = prefs.getString("ordenar_por", "todos");
        pelisLista.clear();
        if ("pelicula".equals(filtro) || "serie".equals(filtro)) {
            String tipo = "pelicula".equals(filtro) ? "Película" : "Serie";
            pelisLista.addAll(db.appDao().getPeliculasByTipo(tipo));
        } else {
            pelisLista.addAll(db.appDao().getAllPeliculas());
        }
        adapter.notifyDataSetChanged();
    }

    private void iniciarServicioBateria() {
        Intent intent = new Intent(this, BatterySmsService.class);
        startService(intent);
    }

    private void aplicarModoOscuro() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkMode = prefs.getBoolean("modo_oscuro", false);
        AppCompatDelegate.setDefaultNightMode(darkMode
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_NUEVA_PELICULA && resultCode == RESULT_OK) {
            cargarPeliculas();
            Toast.makeText(this, R.string.pelicula_guardada, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteConfirmed() {
        if (peliculaPendienteBorrar != null) {
            db.appDao().deletePelicula(peliculaPendienteBorrar);
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
            startActivity(new Intent(this, ActivityPreferencias.class));
            return true;
        }
        if (id == R.id.action_audio) {
            startActivity(new Intent(this, AudioGuideActivity.class));
            return true;
        }
        if (id == R.id.opcion_salir) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

