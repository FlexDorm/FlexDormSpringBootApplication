package com.flexidorm.artsch.security_management.application.dto.response;

import com.flexidorm.artsch.rental_management.domain.enums.EGender;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArrenderResponseDto {
    private Long userId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
    private String address;
    private LocalDate birthDate;
    private String profilePicture;
    private EGender gender;
    private boolean isVerified;
}
