package com.example.cineyseriesjavieralfonsoaguiar2.ui.auth;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9]+@[A-Za-z0-9]+\\.(com|es)$");

    private EmailValidator() {
    }

    public static boolean isValid(String correo) {
        return correo != null && EMAIL_PATTERN.matcher(correo.trim()).matches();
    }
}

