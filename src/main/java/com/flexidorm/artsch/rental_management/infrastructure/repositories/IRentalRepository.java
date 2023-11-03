package com.flexidorm.artsch.rental_management.infrastructure.repositories;

import com.flexidorm.artsch.rental_management.domain.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRentalRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByStudent(String student);


}
