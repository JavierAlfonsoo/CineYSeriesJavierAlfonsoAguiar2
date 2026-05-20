package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.autenticacion;

import java.util.regex.Pattern;

public class ValidadorCorreo {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9]+@[A-Za-z0-9]+\\.(com|es)$");

    private ValidadorCorreo() {
    }

    public static boolean isValid(String correo) {
        return correo != null && EMAIL_PATTERN.matcher(correo.trim()).matches();
    }
}

