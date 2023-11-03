package com.flexidorm.artsch.rental_management.application.services.impl;

import com.flexidorm.artsch.rental_management.application.dto.request.RegisterRentalRequestDto;
import com.flexidorm.artsch.rental_management.application.dto.response.RegisterRentalResponseDto;
import com.flexidorm.artsch.rental_management.application.services.IRentalService;
import com.flexidorm.artsch.rental_management.domain.entities.Reservation;
import com.flexidorm.artsch.rental_management.domain.entities.Student;
import com.flexidorm.artsch.rental_management.infrastructure.repositories.IRentalRepository;
import com.flexidorm.artsch.rental_management.infrastructure.repositories.IRoomRepository;
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
        if(!roomRepository.existsById(request.getRoomId())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The roomId given does not exist");
        }
        if(!userRepository.existsById(request.getStudentId())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The studentId given does not exist");
        }

        //validar que el usuario sea estudiante
        var user = userRepository.findById(request.getStudentId());
        if (user.isPresent()) {
            if (user.get().getClass().getSimpleName().equals("Arrender")) {
                throw new ApplicationException(HttpStatus.BAD_REQUEST, "The user is not a student");
            }
        } else {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "User not found");
        }

        //Validar que la habitacion no este rentada
        var room = roomRepository.findByRoomId(request.getRoomId())
                .orElseThrow(()-> new ApplicationException(HttpStatus.BAD_REQUEST, "The roomId given does not exist"));

        if(room.getStatus().equals("rented")){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The room is already rented");
        }


        //Convertir un user a un student
        var student = modelMapper.map(user, Student.class);

        //Convertir el request (dto) a un objeto de tipo Reservation (entity)
        var rental = modelMapper.map(request, Reservation.class);
        //asignar la habitacion y el estudiante a la reserva, pero antes darle valor al imageUrl de reservation
        rental.setImageUrl(room.getImageUrl());
        rental.setRoom(room);
        rental.setStudent(student);

        //Cambiar el status de la habitacion a rented en la base de datos
        roomRepository.updateStatus(request.getRoomId(), "rented");

        //Guardar la nueva reserva en la base de datos
        var rentalCreated = rentalRepository.save(rental);

        //Convertir el objeto de tipo Reservation (entity) a un objeto de tipo RegisterRentalResponseDto (dto)
        var rentalResponseDto = modelMapper.map(rentalCreated, RegisterRentalResponseDto.class);

        return new ApiResponse<>("Rental was successfully registered", EStatus.SUCCESS, rentalResponseDto);

    }

    @Override
    public ApiResponse<List<RegisterRentalResponseDto>> getRentalsByStudentId(Long studentId) {
        if(!userRepository.existsById(studentId)){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The studentId given does not exist");
        }

        //validar que el usuario sea estudiante
        var user = userRepository.findById(studentId);
        if (user.isPresent()) {
            if (user.get().getClass().getSimpleName().equals("Arrender")) {
                throw new ApplicationException(HttpStatus.BAD_REQUEST, "The user is not a student");
            }
        } else {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "User not found");
        }



        //Obtener las reservas del estudiante
        List<Reservation> rentals = rentalRepository.findByStudentUserId(studentId);

        //Convertir las reservas a un objeto de tipo RegisterRentalResponseDto (dto)
        List<RegisterRentalResponseDto> rentalsResponseDto = rentals.stream()
                .map(rental -> modelMapper.map(rental, RegisterRentalResponseDto.class))
                .toList();
        return new ApiResponse<>("Rentals were successfully obtained", EStatus.SUCCESS, rentalsResponseDto);

    }
}
