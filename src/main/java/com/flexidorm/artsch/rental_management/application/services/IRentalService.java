package com.flexidorm.artsch.rental_management.application.services;

import com.flexidorm.artsch.rental_management.application.dto.request.RegisterRentalRequestDto;
import com.flexidorm.artsch.rental_management.application.dto.response.RegisterRentalResponseDto;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface IRentalService {

    /**
     * Realizar una reserva
     * @param request La reserva a realizar
     * @return La reserva realizada
     */
    ApiResponse<RegisterRentalResponseDto> registerRental(RegisterRentalRequestDto request);

    /**
     * Retornar reservas por estudiante
     * @return La reserva realizada
     */
    ApiResponse<List<RegisterRentalResponseDto>> getRentalsByStudentId(String student);
}
