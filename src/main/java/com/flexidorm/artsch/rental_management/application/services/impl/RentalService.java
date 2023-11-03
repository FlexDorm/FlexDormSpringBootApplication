package com.flexidorm.artsch.rental_management.application.services.impl;

import com.flexidorm.artsch.rental_management.application.dto.request.RegisterRentalRequestDto;
import com.flexidorm.artsch.rental_management.application.dto.response.RegisterRentalResponseDto;
import com.flexidorm.artsch.rental_management.application.services.IRentalService;
import com.flexidorm.artsch.rental_management.domain.entities.Reservation;
import com.flexidorm.artsch.rental_management.domain.entities.Room;
import com.flexidorm.artsch.rental_management.domain.entities.Student;
import com.flexidorm.artsch.rental_management.infrastructure.repositories.IRentalRepository;
import com.flexidorm.artsch.rental_management.infrastructure.repositories.IRoomRepository;
import com.flexidorm.artsch.security_management.application.dto.response.StudentSignUpResponseDto;
import com.flexidorm.artsch.security_management.infrastructure.repositories.IUserRepository;
import com.flexidorm.artsch.shared.exception.ApplicationException;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import com.flexidorm.artsch.shared.model.enums.EStatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RentalService implements IRentalService {
    private final IRentalRepository rentalRepository;

    private final IRoomRepository roomRepository;
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    public RentalService(IRentalRepository rentalRepository, ModelMapper modelMapper, IRoomRepository roomRepository, IUserRepository userRepository) {
        this.rentalRepository = rentalRepository;
        this.modelMapper = modelMapper;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ApiResponse<RegisterRentalResponseDto> registerRental(RegisterRentalRequestDto request) {
        if(!roomRepository.existsById(request.getRoom())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The roomId given does not exist");
        }

        //Validar que la habitacion no este rentada
        var room = roomRepository.findByRoomId(request.getRoom())
                .orElseThrow(()-> new ApplicationException(HttpStatus.BAD_REQUEST, "The roomId given does not exist"));

        if(room.getStatus().equals("rented")){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The room is already rented");
        }

        var reservation = modelMapper.map(request, Reservation.class);
        var reservationCreated = rentalRepository.save(reservation);

        roomRepository.updateStatus(request.getRoom(), "rented");
        
        var reservationResponseDto = modelMapper.map(reservationCreated, RegisterRentalResponseDto.class);

        return new ApiResponse<>("Rental was successfully registered", EStatus.SUCCESS,reservationResponseDto );

    }

    @Override
    public ApiResponse<List<RegisterRentalResponseDto>> getRentalsByStudentId(String student) {

        //Obtener las reservas del estudiante
        List<Reservation> rentals = rentalRepository.findByStudent(student);

        //Convertir las reservas a un objeto de tipo RegisterRentalResponseDto (dto)
        List<RegisterRentalResponseDto> rentalsResponseDto = rentals.stream()
                .map(rental -> modelMapper.map(rental, RegisterRentalResponseDto.class))
                .toList();
        return new ApiResponse<>("Rentals were successfully obtained", EStatus.SUCCESS, rentalsResponseDto);

    }
}
