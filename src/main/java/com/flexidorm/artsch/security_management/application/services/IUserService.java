package com.flexidorm.artsch.security_management.application.services;

import com.flexidorm.artsch.security_management.application.dto.request.*;
import com.flexidorm.artsch.security_management.application.dto.response.ArrenderResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.StudentSignUpResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.UserSignInResponseDto;
import com.flexidorm.artsch.security_management.domain.entities.User;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;

public interface IUserService {
    /**
     * Registra un estudiante
     * @param request El estudiante a registrar
     * @return El estudiante registrado
     */
    ApiResponse<StudentSignUpResponseDto> signUpStudent(SignUpStudentRequestDto request);

    /**
     * Registra un arrendador
     * @param request El arrendador a registrar
     * @return El arrendador registrado
     */
    ApiResponse<ArrenderResponseDto> signUpArrender(SignUpArrenderRequestDto request);

    /**
     * Inicia sesión
     * @param request El usuario a iniciar sesión
     */
    ApiResponse<UserSignInResponseDto> signIn(SignInUserRequestDto request);

    /**
     * Actualiza un estudiante
     * @param request El estudiante a actualizar
     * @param studentId El id del estudiante
     */
    ApiResponse<StudentSignUpResponseDto> updateStudent(UpdateStudentRequestDto request, Long studentId);

    /**
     * Actualiza un arrendador
     * @param request El arrendador a actualizar
     * @param arrenderId El id del arrendador
     */
    ApiResponse<ArrenderResponseDto> updateArrender(UpdateArrenderRequestDto request, Long arrenderId);

    /**
     * Elimina un usuario
     * @param userId El id del usuario
     */
    ApiResponse<Object> deleteUserById(Long userId);
}
