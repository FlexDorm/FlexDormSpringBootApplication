package com.flexidorm.artsch.security_management.application.dto.request;

import com.flexidorm.artsch.rental_management.domain.enums.EGender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateArrenderRequestDto {
    @Size(min = 2, max = 50, message = "Firstname must be between 2 and 50 characters")
    private String firstname;

    @Size(min = 2, max = 50, message = "Lastname must be between 2 and 50 characters")
    private String lastname;

    @Size(min = 9, max = 9, message = "Phone number must be 9 characters")
    @Pattern(regexp = "\\d{9}", message = "Phone number must be 9 digits")
    private String phoneNumber;

    @Email(message = "Email must be valid")
    private String email;
    private String password;
    private String gender;
}
