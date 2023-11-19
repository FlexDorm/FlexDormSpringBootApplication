package com.flexidorm.artsch.security_management.application.services.impl;

import com.flexidorm.artsch.rental_management.domain.entities.Arrender;
import com.flexidorm.artsch.rental_management.domain.entities.Student;
import com.flexidorm.artsch.rental_management.domain.enums.EGender;
import com.flexidorm.artsch.security_management.application.dto.request.*;
import com.flexidorm.artsch.security_management.application.dto.response.ArrenderResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.StudentSignUpResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.UserSignInResponseDto;
import com.flexidorm.artsch.security_management.application.services.IUserService;
import com.flexidorm.artsch.security_management.domain.enums.ERole;
import com.flexidorm.artsch.security_management.domain.jwt.provider.JwtTokenProvider;
import com.flexidorm.artsch.security_management.infrastructure.repositories.IRoleRepository;
import com.flexidorm.artsch.security_management.infrastructure.repositories.IUserRepository;
import com.flexidorm.artsch.shared.exception.ApplicationException;
import com.flexidorm.artsch.shared.exception.ResourceNotFoundException;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import com.flexidorm.artsch.shared.model.enums.EStatus;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    //inyección de dependencias
    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, ModelMapper modelMapper, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

        //encriptar la contraseña desde el request
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        //convertir el request (dto) a un objeto de tipo User (entity)
        var student = modelMapper.map(request, Student.class);
        student.setEnabled(true);

        //establecer los roles
        var roles = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "No se pudo registrar el usuario, no se encontró el rol USER"));
        student.setRoles(Collections.singleton(roles)); //establece un solo rol

        //guarda el Student
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

        //encriptar la contraseña desde el request
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        //convertir el request (dto) a un objeto de tipo User (entity)
        var arrender = modelMapper.map(request, Arrender.class);
        arrender.setEnabled(true);

        //establecer los roles
        var roles = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND, "No se pudo registrar el usuario, no se encontró el rol USER"));
        arrender.setRoles(Collections.singleton(roles)); //establece un solo rol

        //guarda el Arrender
        var arrenderCreated = userRepository.save(arrender);

        //convertir el objeto de tipo User (entity) a un objeto de tipo ArrenderResponseDto (dto)
        var arrenderResponseDto = modelMapper.map(arrenderCreated, ArrenderResponseDto.class);
        return new ApiResponse<>("Arrender was successfully registered", EStatus.SUCCESS, arrenderResponseDto);
    }

    @Override
    public ApiResponse<UserSignInResponseDto> signIn(SignInUserRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        //establece la seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //se obtiene el token
        String token = jwtTokenProvider.generateToken(authentication);

        //se obtiene el usuario autenticado
        var authenticatedUser = (User) authentication.getPrincipal();

        //se obtienen los datos del usuario autenticado
        var authenticatedUserData = userRepository.findByEmail(authenticatedUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("No user with given email was found"));

        //3) convertir el objeto de tipo User (entity) a un objeto de tipo UserResponseDto (dto)
        var userResponseDto = modelMapper.map(authenticatedUserData, UserSignInResponseDto.class);
        userResponseDto.setToken(token);

        //4) obtener el tipo de usuario
        var dtype = userRepository.findByUserId(authenticatedUserData.getUserId()).get().getClass().getSimpleName();
        userResponseDto.setDtype(dtype);

        //retornar la respuesta
        return new ApiResponse<>("OK", EStatus.SUCCESS, userResponseDto);
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


    @Override
    public boolean doesArrenderExist(Long arrenderId) {
        return userRepository.existsByUserId(arrenderId);
    }

    @Override
    public Arrender getArrenderById(Long arrenderId) {
        var arrender = userRepository.findByUserId(arrenderId)
                .orElseThrow(() -> new ResourceNotFoundException("No arrender with given id was found"));
        var userResponseDto = modelMapper.map(arrender, Arrender.class);

        return new ApiResponse<>("Arrender was successfully returned", EStatus.SUCCESS, userResponseDto).getData();
    }

}
