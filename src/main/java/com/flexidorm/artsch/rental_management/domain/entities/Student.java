package com.flexidorm.artsch.rental_management.domain.entities;

import com.flexidorm.artsch.security_management.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student extends User {
    @Column(length = 50)
    private String university;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isVerified;

    /**
     * -Info: UN "estudiante" puede tener MUCHAS "reservas"
     * -MappedBy: estará mapeando por el atributo "student" de la clase Reservation
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();
}
