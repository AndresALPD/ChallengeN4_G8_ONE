package com.andres.forohub.controllers;

import com.andres.forohub.dto.DatosAutenticacion;
import com.andres.forohub.dto.DatosJWTToken;
import com.andres.forohub.entities.Usuario;
import com.andres.forohub.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacion datosAutenticacion) {
        try {
            System.out.println("=== DEBUG LOGIN ===");
            System.out.println("Email recibido: " + datosAutenticacion.email());
            System.out.println("Password recibido: " + datosAutenticacion.password());

            // Crear token de autenticación con email y password
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            datosAutenticacion.email(),
                            datosAutenticacion.password()
                    );

            System.out.println("Token de autenticación creado: " + authToken);

            // Autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(authToken);
            System.out.println("Autenticación exitosa: " + authentication.isAuthenticated());

            // Obtener el usuario autenticado
            Usuario usuario = (Usuario) authentication.getPrincipal();
            System.out.println("Usuario autenticado: " + usuario.getEmail());

            // Generar token JWT
            String jwtToken = tokenService.generarToken(usuario);
            System.out.println("Token JWT generado");

            // Crear respuesta con datos del token y usuario
            DatosJWTToken respuesta = new DatosJWTToken(
                    jwtToken,
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getEmail()
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            // En caso de credenciales inválidas
            System.out.println("=== ERROR LOGIN ===");
            System.out.println("Tipo de excepción: " + e.getClass().getSimpleName());
            System.out.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }
    }
}