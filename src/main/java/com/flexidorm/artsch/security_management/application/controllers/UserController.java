package com.flexidorm.artsch.security_management.application.controllers;

import com.flexidorm.artsch.security_management.application.dto.request.SignInUserRequestDto;
import com.flexidorm.artsch.security_management.application.dto.request.SignUpArrenderRequestDto;
import com.flexidorm.artsch.security_management.application.dto.request.SignUpStudentRequestDto;
import com.flexidorm.artsch.security_management.application.dto.response.ArrenderResponseDto;
import com.flexidorm.artsch.security_management.application.dto.response.StudentSignUpResponseDto;
import com.flexidorm.artsch.security_management.application.services.IUserService;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Sign up a student")
    @PostMapping("signUp/student")
    public ResponseEntity<ApiResponse<StudentSignUpResponseDto>> signUpStudent(@Valid @RequestBody SignUpStudentRequestDto request) {
        var res = userService.signUpStudent(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Sign up an arrender")
    @PostMapping("signUp/arrender")
    public ResponseEntity<ApiResponse<ArrenderResponseDto>> signUpArrender(@Valid @RequestBody SignUpArrenderRequestDto request) {
        var res = userService.signUpArrender(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Sign in a user")
    @PostMapping("/signIn")
    public ResponseEntity<?> signInUser(@Valid @RequestBody SignInUserRequestDto request){
        var res = userService.signIn(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
