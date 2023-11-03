package com.flexidorm.artsch.rental_management.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRoomResponseDto {

    private Long roomId;
    private String title;
    private String description;
    private String address;
    private String imageUrl;
    private double price;
    private String nearUniversities;
    private Long arrenderId;

}
