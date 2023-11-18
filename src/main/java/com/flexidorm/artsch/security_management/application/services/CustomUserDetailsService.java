package com.flexidorm.artsch.security_management.application.services;

import com.flexidorm.artsch.security_management.infrastructure.repositories.IUserRepository;
import com.flexidorm.artsch.shared.util.Utilities;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserRepository userRepository;

    public CustomUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Obtiene los datos del usuario autenticado por su username o email
     * @param email Nombre de usuario o email del usuario autenticado
     * @return Datos del usuario autenticado
     * @throws UsernameNotFoundException Excepción de usuario no encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //busca al usuario por su username o email
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró al usuario con el email: " + email));

        //crea y retorna un objeto que representa al usuario autenticado
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Utilities.mapRoles(user.getRoles()))
                .disabled(!user.isEnabled())
                .build();
    }
}
