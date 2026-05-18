package com.example.cineyseriesjavieralfonsoaguiar2.ui.main;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.maps.MapaActivity;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas.RecomendacionesActivity;
import com.google.android.material.navigation.NavigationView;

public abstract class BaseDrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View content = getLayoutInflater().inflate(layoutResID, null, false);

        drawerLayout = new DrawerLayout(this);
        drawerLayout.setLayoutParams(new DrawerLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.MATCH_PARENT));

        LinearLayout mainContainer = new LinearLayout(this);
        mainContainer.setOrientation(LinearLayout.VERTICAL);
        drawerLayout.addView(mainContainer, new DrawerLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.MATCH_PARENT));

        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle(getTitle());
        TypedValue colorPrimary = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.colorPrimary, colorPrimary, true);
        toolbar.setBackgroundColor(colorPrimary.data);
        toolbar.setTitleTextColor(getColor(android.R.color.white));
        mainContainer.addView(toolbar, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getResources().getDimensionPixelSize(androidx.appcompat.R.dimen.abc_action_bar_default_height_material)));
        setSupportActionBar(toolbar);

        FrameLayout contentFrame = new FrameLayout(this);
        contentFrame.addView(content);
        mainContainer.addView(contentFrame, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1));

        NavigationView navigationView = new NavigationView(this);
        navigationView.inflateMenu(R.menu.menu_drawer);
        DrawerLayout.LayoutParams navParams = new DrawerLayout.LayoutParams(
                DrawerLayout.LayoutParams.WRAP_CONTENT,
                DrawerLayout.LayoutParams.MATCH_PARENT);
        navParams.gravity = android.view.Gravity.START;
        drawerLayout.addView(navigationView, navParams);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            drawerLayout.closeDrawers();
            if (id == R.id.nav_recomendaciones) {
                abrirSiNoActual(RecomendacionesActivity.class);
            } else if (id == R.id.nav_ayuda) {
                abrirSiNoActual(AyudaActivity.class);
            } else if (id == R.id.nav_acerca) {
                abrirSiNoActual(AcercaDeActivity.class);
            } else if (id == R.id.nav_mapa) {
                abrirSiNoActual(MapaActivity.class);
            } else if (id == R.id.nav_volver) {
                finish();
            }
            return true;
        });

        super.setContentView(drawerLayout);
    }

    protected void setDrawerTitle(int titleResId) {
        setTitle(titleResId);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleResId);
        }
    }

    private void abrirSiNoActual(Class<?> destino) {
        if (destino.equals(getClass())) {
            return;
        }
        startActivity(new Intent(this, destino));
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(android.view.Gravity.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}

