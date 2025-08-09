package com.andres.forohub.dto;

public record DatosJWTToken(
        String token,
        String tipo,
        Long usuarioId,
        String nombreUsuario,
        String email
) {
    public DatosJWTToken(String token, Long usuarioId, String nombreUsuario, String email) {
        this(token, "Bearer", usuarioId, nombreUsuario, email);
    }
}