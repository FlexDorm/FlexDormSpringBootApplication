package com.flexidorm.artsch.rental_management.application.dto.request;

import com.flexidorm.artsch.rental_management.domain.enums.EPaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class RegisterRentalRequestDto {

    private Date date;

    @NotBlank(message = "Phone is required")
    @Size(min = 9, max = 9, message = "Phone must be between 9 characters")
    private String phone;

    @NotBlank(message = "Email is required")
    @Size(min = 2, max = 50, message = "Email must be between 2 and 50 characters")
    private String email;

    @NotBlank(message = "Observation is required")
    @Size(min = 2, max = 500, message = "Observation must be between 2 and 500 characters")
    private String observation;

    private double totalPrice;

    private String hourInit;

    private String hourFinal;

    private String student;

    private Long room;

    private String imageUrl;

    private boolean favorite;

    private String arrenderId;

    private String moviment;


}