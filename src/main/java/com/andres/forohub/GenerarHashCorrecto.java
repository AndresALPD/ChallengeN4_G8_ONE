// Ejecuta este código para generar el hash correcto
package com.andres.forohub;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHashCorrecto {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "secret";
        String hash = encoder.encode(password);

        System.out.println("Contraseña: " + password);
        System.out.println("Hash generado: " + hash);

        // Verificar que funciona
        boolean matches = encoder.matches(password, hash);
        System.out.println("¿Coincide? " + matches);

        // SQL para actualizar
        System.out.println("\nEjecuta este SQL:");
        System.out.println("UPDATE usuarios SET password = '" + hash + "' WHERE email = 'admin@forohub.com';");
    }
}