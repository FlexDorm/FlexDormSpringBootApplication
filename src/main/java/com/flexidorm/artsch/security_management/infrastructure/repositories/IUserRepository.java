package com.flexidorm.artsch.security_management.infrastructure.repositories;

import com.flexidorm.artsch.security_management.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    /**
     * Verifica si existe un usuario con el email dado
     * @param email Email
     * @return Un booleano
     */
    boolean existsByEmail(String email);

    /**
     * Verifica si existe un usuario con el número de celular dado
     * @param phoneNumber El número de celular
     * @return boolean
     */
    boolean existsByPhoneNumber(String phoneNumber);

    /**
     * Verifica si existe un usuario con el nombre de usuario dado
     * @param username Nombre de usuario
     * @return Un booleano
     */
    boolean existsByUsername(String username);

    /**
     * Obtiene un usuario con el email dado
     * @param email Email
     * @return Un usuario
     */
    Optional<User> findByEmail(String email);


    /**
     * Verifica si existe un usuario con el id de arrender dado
     * @param arrenderId El id de arrender
     * @return boolean
     */
    boolean existsByUserId(Long arrenderId);

    /**
     * Obtiene un usuario con el id de arrender dado
     * @param arrenderId El id de arrender
     * @return Un usuario
     */
    Optional<User> findByUserId(Long arrenderId);


}
