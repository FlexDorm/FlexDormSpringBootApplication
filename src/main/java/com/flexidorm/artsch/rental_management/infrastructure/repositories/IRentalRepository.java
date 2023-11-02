package com.flexidorm.artsch.rental_management.infrastructure.repositories;

import com.flexidorm.artsch.rental_management.domain.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRentalRepository extends JpaRepository<Reservation, Long> {
    /**
     * Verifica si existe una reserva con el id de estudiante dado
     * @param studentId El id de estudiante
     * @return boolean
     */
    List<Reservation> findByStudentUserId(Long studentId);
}
