package com.andres.forohub.services;

import com.andres.forohub.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=== DEBUG AutenticacionService ===");
        System.out.println("Buscando usuario con email: " + username);

        UserDetails usuario = usuarioRepository.findByEmail(username);

        if (usuario == null) {
            System.out.println("Usuario NO encontrado para email: " + username);
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        System.out.println("Usuario encontrado:");
        System.out.println("- Email: " + usuario.getUsername());
        System.out.println("- Password hash: " + usuario.getPassword());
        System.out.println("- Activo: " + usuario.isEnabled());
        System.out.println("- Authorities: " + usuario.getAuthorities());

        return usuario;
    }
}