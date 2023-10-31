package com.flexidorm.artsch.security_management.application.dto.response;

import com.flexidorm.artsch.rental_management.domain.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignInResponseDto {
    private Long userId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
    private EGender gender;
    private String university;
    private boolean isVerified;
}
