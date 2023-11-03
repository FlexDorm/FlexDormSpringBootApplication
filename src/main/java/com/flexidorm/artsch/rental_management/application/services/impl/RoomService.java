package com.flexidorm.artsch.rental_management.application.services.impl;

import com.flexidorm.artsch.rental_management.application.dto.request.RegisterRoomRequestDto;
import com.flexidorm.artsch.rental_management.application.dto.response.RegisterRoomResponseDto;
import com.flexidorm.artsch.rental_management.application.services.IRoomService;
import com.flexidorm.artsch.rental_management.domain.entities.Room;
import com.flexidorm.artsch.rental_management.infrastructure.repositories.IRoomRepository;
import com.flexidorm.artsch.security_management.application.services.IUserService;
import com.flexidorm.artsch.shared.exception.ApplicationException;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import com.flexidorm.artsch.shared.model.enums.EStatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {

    private final IRoomRepository roomRepository;

    private final IUserService userService;
    private final ModelMapper modelMapper;

    public RoomService(IRoomRepository roomRepository, ModelMapper modelMapper, IUserService userService) {
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public ApiResponse<RegisterRoomResponseDto> registerRoom(RegisterRoomRequestDto request) {
        // Validar que el arrenderId exista
        if (!userService.doesArrenderExist(request.getArrenderId())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The arrenderId given does not exist");
        }

        // Crear una nueva instancia de Room y configurar sus propiedades
        Room room = new Room();
        room.setTitle(request.getTitle());
        room.setDescription(request.getDescription());
        room.setImageUrl(request.getImageUrl());
        room.setAddress(request.getAddress());
        room.setNearUniversities(request.getNearUniversities());
        room.setPrice(request.getPrice());
        room.setStatus("free");
        room.setArrender(userService.getArrenderById(request.getArrenderId()));

        // Guardar la nueva habitación en la base de datos
        Room roomCreated = roomRepository.save(room);

        // Convertir el objeto de tipo Room (entity) a un objeto de tipo RegisterRoomResponseDto (dto)
        RegisterRoomResponseDto roomResponseDto = modelMapper.map(roomCreated, RegisterRoomResponseDto.class);

        return new ApiResponse<>("Room was successfully registered", EStatus.SUCCESS, roomResponseDto);
    }

    @Override
    public ApiResponse<List<RegisterRoomResponseDto>> getRoomsByRenderId(Long renderId) {
        //Validar que el renderID exista
        if (!roomRepository.existsByArrenderUserId(renderId)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The arrenderId given does not exist");
        }

        // Traer todas las habitaciones con el renderId dado
        List<Room> rooms = roomRepository.findByArrenderUserId(renderId);

        // Convertir la lista de objetos de tipo Room (entity) a una lista de objetos de tipo RegisterRoomResponseDto (dto)
        List<RegisterRoomResponseDto> roomResponseList = rooms.stream()
                .map(room -> modelMapper.map(room, RegisterRoomResponseDto.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("Rooms were successfully returned", EStatus.SUCCESS, roomResponseList);
    }

    @Override
    public ApiResponse<List<RegisterRoomResponseDto>>getByState(String status){

        List<Room> rooms=roomRepository.findByStatus(status);

        List<RegisterRoomResponseDto> roomResponseList = rooms.stream()
                .map(room -> modelMapper.map(room, RegisterRoomResponseDto.class))
                .collect(Collectors.toList());

        return new ApiResponse<>("Rooms were successfully returned", EStatus.SUCCESS, roomResponseList);
    }

/**
 @Override
 public ApiResponse<UpdateRoomResponseDto> updateRoom(UpdateRoomRequestDto request, Long roomId) {
 return null;
 }

 @Override
 public ApiResponse<Object> logicDeleteRoomById(Long roomId) {
 return null;
 }
 **/
}