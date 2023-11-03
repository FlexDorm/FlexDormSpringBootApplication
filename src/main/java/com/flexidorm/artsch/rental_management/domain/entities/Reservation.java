package com.flexidorm.artsch.rental_management.domain.entities;

import com.flexidorm.artsch.rental_management.domain.enums.EPaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(nullable = false)
    private int stayHours;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private LocalDateTime checkInDate;

    @Column(nullable = false)
    private LocalDateTime checkOutDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EPaymentMethod paymentMethod;

    //MUCHAS "reservas" van a estar en UN "estudiante"
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    //MUCHAS "reservas" van a estar en UN "habitación"
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
}
