package com.flexidorm.artsch.rental_management.infrastructure.repositories;

import com.flexidorm.artsch.rental_management.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        List<Room> findByStatus(String status);
        //Cambiar el status de la habitación
        @Transactional
        @Modifying
        @Query("UPDATE Room r SET r.status = :status WHERE r.roomId = :roomId")
        void updateStatus(Long roomId, String status);

        //Retornar habitaciones por id
        Optional<Room> findByRoomId(Long roomId);

}
