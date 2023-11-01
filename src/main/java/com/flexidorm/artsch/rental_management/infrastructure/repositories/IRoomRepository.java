package com.flexidorm.artsch.rental_management.infrastructure.repositories;

import com.flexidorm.artsch.rental_management.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoomRepository extends JpaRepository<Room, Long> {

        /**
        * Verifica si existe una habitación con el id de arrender dado
        * @param arrenderUserId El id de arrender
        * @return boolean
        */
        boolean existsByArrenderUserId(Long arrenderUserId);

        /**
        * Obtiene habitaciones con el id de arrender dado
        * @param arrenderUserId El id de arrender
        * @return Lista de habitaciones
        */
        List<Room> findByArrenderUserId(Long arrenderUserId);
}
