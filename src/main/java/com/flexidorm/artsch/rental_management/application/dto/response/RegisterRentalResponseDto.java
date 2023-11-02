package com.flexidorm.artsch.rental_management.application.dto.response;

import com.flexidorm.artsch.rental_management.domain.enums.EPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRentalResponseDto {

    private Long reservationId;
    private Date date;
    private String phone;
    private String email;
    private String observation;
    private double totalPrice;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String imageUrl;
    private EPaymentMethod paymentMethod;
    private Long studentId;
    private Long roomId;


}