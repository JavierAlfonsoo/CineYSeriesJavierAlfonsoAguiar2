package com.example.cineyseriesjavieralfonsoaguiar2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private RecyclerView recyclerPelis;
    ArrayList<Pelicula> pelisLista;
    private FloatingActionButton fab;

    // Constante para el código de solicitud
    private static final int CODIGO_NUEVA_PELICULA = 100;
    private PeliculaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPelis = findViewById(R.id.rvPelisSeries);
        recyclerPelis.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.floatingActionButton);

        pelisLista = new ArrayList<Pelicula>();

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
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, NuevaPeliculaActivity.class);
            startActivityForResult(intent, CODIGO_NUEVA_PELICULA);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_NUEVA_PELICULA && resultCode == RESULT_OK) {
            Pelicula nuevaPelicula = (Pelicula) data.getSerializableExtra("nueva_pelicula");
            if (nuevaPelicula != null) {
                pelisLista.add(nuevaPelicula);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Película añadida: " + nuevaPelicula.getNombre(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // inflater para el menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    // Funcionalidad del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcion_salir) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}