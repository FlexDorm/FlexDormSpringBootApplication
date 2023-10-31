package com.flexidorm.artsch.security_management.application.services.impl;

import com.flexidorm.artsch.rental_management.domain.entities.Arrender;
import com.flexidorm.artsch.rental_management.domain.entities.Student;
import com.flexidorm.artsch.security_management.application.dto.request.SignInUserRequestDto;
import com.flexidorm.artsch.security_management.application.dto.request.SignUpArrenderRequestDto;
import com.flexidorm.artsch.security_management.application.dto.request.SignUpStudentRequestDto;
import com.flexidorm.artsch.security_management.application.dto.response.ArrenderResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.StudentSignUpResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.UserSignInResponseDto;
import com.flexidorm.artsch.security_management.application.services.IUserService;
import com.flexidorm.artsch.security_management.domain.entities.User;
import com.flexidorm.artsch.security_management.infrastructure.repositories.IUserRepository;
import com.flexidorm.artsch.shared.exception.ResourceNotFoundException;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import com.flexidorm.artsch.shared.model.enums.EStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final ModelMapper modelMapper;

    //inyección de dependencias
    public UserService(IUserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<StudentSignUpResponseDto> signUpStudent(SignUpStudentRequestDto request) {
        //convertir el request (dto) a un objeto de tipo User (entity)
        var student = modelMapper.map(request, Student.class);
        var studentCreated = userRepository.save(student);

        //convertir el objeto de tipo User (entity) a un objeto de tipo StudentResponseDto (dto)
        var studentResponseDto = modelMapper.map(studentCreated, StudentSignUpResponseDto.class);
        return new ApiResponse<>("Estudiante registrado con éxito", EStatus.SUCCESS, studentResponseDto);
    }

    @Override
    public ApiResponse<ArrenderResponseDto> signUpArrender(SignUpArrenderRequestDto request) {
        //convertir el request (dto) a un objeto de tipo User (entity)
        var arrender = modelMapper.map(request, Arrender.class);
        var arrenderCreated = userRepository.save(arrender);

        //convertir el objeto de tipo User (entity) a un objeto de tipo ArrenderResponseDto (dto)
        var arrenderResponseDto = modelMapper.map(arrenderCreated, ArrenderResponseDto.class);
        return new ApiResponse<>("Arrendador registrado con éxito", EStatus.SUCCESS, arrenderResponseDto);
    }

    @Override
    public ApiResponse<UserSignInResponseDto> signIn(SignInUserRequestDto request) {
        //1) se verifica si existe un usuario con el email dado
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el email dado"));

        //2) convertir el objeto de tipo User (entity) a un objeto de tipo UserResponseDto (dto)
        var userResponseDto = modelMapper.map(user, UserSignInResponseDto.class);

        //3) se verifica si la contraseña dada es igual a la contraseña del usuario
        if (user.getPassword().equals(request.getPassword())) {
            return new ApiResponse<>("Usuario autenticado con éxito", EStatus.SUCCESS, userResponseDto);
        } else {
            return new ApiResponse<>("Contraseña incorrecta", EStatus.ERROR, null);
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUserById(Long userId) {

    }
}
