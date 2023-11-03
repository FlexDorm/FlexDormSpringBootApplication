package com.flexidorm.artsch.rental_management.application.services;

import com.flexidorm.artsch.rental_management.application.dto.request.RegisterRoomRequestDto;
import com.flexidorm.artsch.rental_management.application.dto.response.RegisterRoomResponseDto;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface IRoomService {

    /**
     * Registra una habitación
     * @param request La habitación a registrar
     * @return La habitación registrada
     */
    ApiResponse<RegisterRoomResponseDto> registerRoom(RegisterRoomRequestDto request);

    /**
     * Retornar  habitaciones por renderID
     * @return La habitación registrada
     */
    ApiResponse<List<RegisterRoomResponseDto>> getRoomsByRenderId(Long renderId);

    ApiResponse<List<RegisterRoomResponseDto>> getByState(String state);
}
