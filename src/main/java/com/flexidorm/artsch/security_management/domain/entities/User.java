package com.flexidorm.artsch.security_management.domain.entities;

import com.flexidorm.artsch.rental_management.domain.enums.EGender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false, length = 9, unique = true)
    private String phoneNumber;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    //se tomará como un String los valores de este enum
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EGender gender;
}
