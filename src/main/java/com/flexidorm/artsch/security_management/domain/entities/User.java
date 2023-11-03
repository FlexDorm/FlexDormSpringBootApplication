package com.flexidorm.artsch.security_management.domain.entities;

import com.flexidorm.artsch.rental_management.domain.enums.EGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
//se creará una sola tabla para todas las clases que hereden de esta
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 100)
    private String firstname;

    @Column(nullable = false, length = 100)
    private String lastname;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 9, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column (nullable = false)
    private String profilePicture;

    @Column(nullable = false)
    private boolean isEnabled;
    
    //se tomará como un String los valores de este enum
    @Enumerated(EnumType.STRING)
    private EGender gender;
}
