package com.flexidorm.artsch.security_management.application.dto.response;

import com.flexidorm.artsch.rental_management.domain.entities.Room;
import com.flexidorm.artsch.rental_management.domain.enums.EPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationResponseDto {
    private Long reservationId;
    private int stayHours;
    private double totalPrice;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private EPaymentMethod paymentMethod;
    private StudentSignUpResponseDto student;
//    private Room room;
}
