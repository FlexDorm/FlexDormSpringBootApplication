package com.flexidorm.artsch.rental_management.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRoomRequestDto {
    @NotBlank(message = "Room title is required")
    @Size(min = 2, max = 50, message = "Room title must be between 2 and 50 characters")
    private String title;

    @NotBlank(message = "Room description is required")
    @Size(min = 2, max = 500, message = "Room description must be between 2 and 500 characters")
    private String description;

    @NotBlank(message = "Room address is required")
    @Size(min = 2, max = 50, message = "Room address must be between 2 and 50 characters")
    private String address;

    @NotBlank(message = "Room imageUrl is required")
    @Size(min = 2, max = 500, message = "Room imageUrl must be between 2 and 500 characters")
    private String imageUrl;


    private double price;

    @NotBlank(message = "Room nearUniversities is required")
    @Size(min = 2, max = 500, message = "Room nearUniversities must be between 2 and 500 characters")
    private String nearUniversities;

    private Long arrenderId;

}