package com.flexidorm.artsch.security_management.application.services.impl;

import com.flexidorm.artsch.rental_management.domain.entities.Arrender;
import com.flexidorm.artsch.rental_management.domain.entities.Student;
import com.flexidorm.artsch.rental_management.domain.enums.EGender;
import com.flexidorm.artsch.security_management.application.dto.request.*;
import com.flexidorm.artsch.security_management.application.dto.response.ArrenderResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.StudentSignUpResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.UserSignInResponseDto;
import com.flexidorm.artsch.security_management.application.services.IUserService;
import com.flexidorm.artsch.security_management.infrastructure.repositories.IUserRepository;
import com.flexidorm.artsch.shared.exception.ApplicationException;
import com.flexidorm.artsch.shared.exception.ResourceNotFoundException;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import com.flexidorm.artsch.shared.model.enums.EStatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        //validar que el email y el número de celular no están registrados
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The email given is already registered");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw  new ApplicationException(HttpStatus.BAD_REQUEST, "The phone number given is already registered");
        }
        if (userRepository.existsByUsername(request.getUsername())){
            throw  new ApplicationException(HttpStatus.BAD_REQUEST, "The username given is already registered");
        }

        //convertir el request (dto) a un objeto de tipo User (entity)
        var student = modelMapper.map(request, Student.class);
        student.setEnabled(true);
        var studentCreated = userRepository.save(student);


        //convertir el objeto de tipo User (entity) a un objeto de tipo StudentResponseDto (dto)
        var studentResponseDto = modelMapper.map(studentCreated, StudentSignUpResponseDto.class);
        return new ApiResponse<>("Student was successfully registered", EStatus.SUCCESS, studentResponseDto);
    }

    @Override
    public ApiResponse<ArrenderResponseDto> signUpArrender(SignUpArrenderRequestDto request) {
        //validar que el email y el número de celular no estén registrados
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The email given is already registered");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The phone number given is already registered");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The username given is already registered");
        }

        //convertir el request (dto) a un objeto de tipo User (entity)
        var arrender = modelMapper.map(request, Arrender.class);
        arrender.setEnabled(true);
        var arrenderCreated = userRepository.save(arrender);

        //convertir el objeto de tipo User (entity) a un objeto de tipo ArrenderResponseDto (dto)
        var arrenderResponseDto = modelMapper.map(arrenderCreated, ArrenderResponseDto.class);
        return new ApiResponse<>("Arrender was successfully registered", EStatus.SUCCESS, arrenderResponseDto);
    }

    @Override
    public ApiResponse<UserSignInResponseDto> signIn(SignInUserRequestDto request) {
        //1) se verifica si existe un usuario con el email dado
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No user with given email was found"));

        //2) se valida que el usuario esté habilitado
        if (!user.isEnabled()) {
            throw new ApplicationException(HttpStatus.NOT_ACCEPTABLE, "The user given is not enabled");
        }

        //3) convertir el objeto de tipo User (entity) a un objeto de tipo UserResponseDto (dto)
        var userResponseDto = modelMapper.map(user, UserSignInResponseDto.class);
        userResponseDto.setDtype(user.getClass().getSimpleName());

        //4) se verifica si la contraseña dada es igual a la contraseña del usuario
        if (user.getPassword().equals(request.getPassword())) {
            return new ApiResponse<>("Successfully authenticated user", EStatus.SUCCESS, userResponseDto);
        } else {
            return new ApiResponse<>("Wrong password", EStatus.ERROR, null);
        }
    }

    @Override
    public ApiResponse<StudentSignUpResponseDto> updateStudent(UpdateStudentRequestDto request, Long studentId) {
        //1) se verifica si existe un usuario con el id dado
        var user = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("No user with given id was found"));

        //se valida que sea un estudiante
        if (!(user instanceof Student)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The user given is not an student");
        }

        //2) actualiza los datos
        user.setFirstname(StringUtils.hasText(request.getFirstname()) ? request.getFirstname() : user.getFirstname());
        user.setLastname(StringUtils.hasText(request.getLastname()) ? request.getLastname() : user.getLastname());
        user.setUsername(StringUtils.hasText(request.getUsername()) ? request.getUsername() : user.getUsername());
        user.setPhoneNumber(StringUtils.hasText(request.getPhoneNumber()) ? request.getPhoneNumber() : user.getPhoneNumber());
        user.setEmail(StringUtils.hasText(request.getEmail()) ? request.getEmail() : user.getEmail());
        user.setPassword(StringUtils.hasText(request.getPassword()) ? request.getPassword() : user.getPassword());
        user.setAddress(StringUtils.hasText(request.getAddress()) ? request.getAddress() : user.getAddress());
        user.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : user.getBirthDate());
        user.setProfilePicture(StringUtils.hasText(request.getProfilePicture()) ? request.getProfilePicture() : user.getProfilePicture());
        user.setGender(StringUtils.hasText(request.getGender()) ? EGender.valueOf(request.getGender()) : user.getGender());
        ((Student) user).setUniversity(StringUtils.hasText(request.getUniversity()) ? request.getUniversity() : ((Student) user).getUniversity());

        //3) se actualiza el usuario
        var studentUpdated = userRepository.save(user);

        //4) convertir el objeto de tipo User (entity) a un objeto de tipo StudentResponseDto (dto)
        var studentResponseDto = modelMapper.map(studentUpdated, StudentSignUpResponseDto.class);
        return new ApiResponse<>("Student updated successfully", EStatus.SUCCESS, studentResponseDto);
    }

    @Override
    public ApiResponse<ArrenderResponseDto> updateArrender(UpdateArrenderRequestDto request, Long arrenderId) {
        //1) se verifica si existe un usuario con el id dado
        var user = userRepository.findById(arrenderId)
                .orElseThrow(() -> new ResourceNotFoundException("No user with given id was found"));

        //se valida que sea un arrendador
        if (!(user instanceof Arrender)) {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "The user given is not an arrender");
        }

        //2) actualiza los datos
        user.setFirstname(StringUtils.hasText(request.getFirstname()) ? request.getFirstname() : user.getFirstname());
        user.setLastname(StringUtils.hasText(request.getLastname()) ? request.getLastname() : user.getLastname());
        user.setUsername(StringUtils.hasText(request.getUsername()) ? request.getUsername() : user.getUsername());
        user.setPhoneNumber(StringUtils.hasText(request.getPhoneNumber()) ? request.getPhoneNumber() : user.getPhoneNumber());
        user.setEmail(StringUtils.hasText(request.getEmail()) ? request.getEmail() : user.getEmail());
        user.setPassword(StringUtils.hasText(request.getPassword()) ? request.getPassword() : user.getPassword());
        user.setAddress(StringUtils.hasText(request.getAddress()) ? request.getAddress() : user.getAddress());
        user.setBirthDate(request.getBirthDate() != null ? request.getBirthDate() : user.getBirthDate());
        user.setProfilePicture(StringUtils.hasText(request.getProfilePicture()) ? request.getProfilePicture() : user.getProfilePicture());
        user.setGender(StringUtils.hasText(request.getGender()) ?  EGender.valueOf(request.getGender()) : user.getGender());

        //3) se actualiza el usuario
        var arrenderUpdated = userRepository.save(user);

        //4) convertir el objeto de tipo User (entity) a un objeto de tipo StudentResponseDto (dto)
        var arrenderResponseDto = modelMapper.map(arrenderUpdated, ArrenderResponseDto.class);
        return new ApiResponse<>("Arrender updated successfully", EStatus.SUCCESS, arrenderResponseDto);
    }

    @Override
    public ApiResponse<Object> logicDeleteUserById(Long userId) {
        //1) se verifica si existe un usuario con el id dado
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user with given id was found"));

        //2) se elimina lógicamente el usuario
        user.setEnabled(false);
        userRepository.save(user);

        return new ApiResponse<>("User was successfully deleted", EStatus.SUCCESS, null);
    }

    @Override
    public ApiResponse<UserSignInResponseDto> reactivateAccount(Long userId) {
        //1) se verifica si existe un usuario con el id dado
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No user with given id was found"));

        //2) se activa la cuenta del usuario
        user.setEnabled(true);

        //3) guardar cambios
        var reactivatedUser = userRepository.save(user);

        //4) convertir el objeto de tipo User (entity) a un objeto de tipo UserResponseDto (dto)
        var userResponseDto = modelMapper.map(reactivatedUser, UserSignInResponseDto.class);

        return new ApiResponse<>("User was successfully reactivated", EStatus.SUCCESS, userResponseDto);
    }

}
