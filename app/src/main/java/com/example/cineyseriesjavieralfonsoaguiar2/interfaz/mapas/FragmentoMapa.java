package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.mapas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class FragmentoMapa extends Fragment implements OnMapReadyCallback {

    private GoogleMap mapa;
    private final ActivityResultLauncher<String[]> pedirPermisosUbicacion =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), resultado -> {
                boolean permisoConcedido = Boolean.TRUE.equals(resultado.get(Manifest.permission.ACCESS_FINE_LOCATION))
                        || Boolean.TRUE.equals(resultado.get(Manifest.permission.ACCESS_COARSE_LOCATION));
                if (permisoConcedido) {
                    activarUbicacion();
                } else {
                    Toast.makeText(requireContext(), R.string.mapa_permiso_denegado, Toast.LENGTH_SHORT).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflador, @Nullable ViewGroup contenedor,
                             @Nullable Bundle estadoGuardado) {
        return inflador.inflate(R.layout.fragment_mapa, contenedor, false);
    }

    @Override
    public void onViewCreated(@NonNull View vista, @Nullable Bundle estadoGuardado) {
        super.onViewCreated(vista, estadoGuardado);
        SupportMapFragment fragmentoMapa = SupportMapFragment.newInstance();
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.mapa_google_container, fragmentoMapa)
                .commit();
        fragmentoMapa.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mapa = googleMap;
        configurarMapa();
        solicitarUbicacionSiHaceFalta();
    }

    private void configurarMapa() {
        LatLng madrid = new LatLng(40.4190, -3.7058);
        LatLng barcelona = new LatLng(41.4036, 2.1569);
        LatLng valencia = new LatLng(39.4704, -0.3768);

        mapa.addMarker(new MarkerOptions().position(madrid).title(getString(R.string.mapa_cine_madrid)));
        mapa.addMarker(new MarkerOptions().position(barcelona).title(getString(R.string.mapa_cine_barcelona)));
        mapa.addMarker(new MarkerOptions().position(valencia).title(getString(R.string.mapa_cine_valencia)));
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(madrid, 5.8f));

        mapa.setOnMarkerClickListener(marcador -> {
            abrirRuta(marcador);
            return false;
        });
    }

    private void solicitarUbicacionSiHaceFalta() {
        if (tienePermisoUbicacion()) {
            activarUbicacion();
            return;
        }
        pedirPermisosUbicacion.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }

    private boolean tienePermisoUbicacion() {
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void activarUbicacion() {
        if (mapa == null || !tienePermisoUbicacion()) {
            return;
        }
        mapa.setMyLocationEnabled(true);
    }

    private void abrirRuta(@NonNull Marker marcador) {
        LatLng destino = marcador.getPosition();
        Toast.makeText(requireContext(), getString(R.string.mapa_ruta, marcador.getTitle()), Toast.LENGTH_SHORT).show();
        Uri uri = Uri.parse("google.navigation:q=" + destino.latitude + "," + destino.longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(requireActivity().getPackageManager()) == null) {
            intent.setPackage(null);
        }
        startActivity(intent);
    }
}
