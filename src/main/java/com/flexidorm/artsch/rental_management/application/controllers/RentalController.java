package com.flexidorm.artsch.rental_management.application.controllers;

import com.flexidorm.artsch.rental_management.application.dto.request.RegisterRentalRequestDto;
import com.flexidorm.artsch.rental_management.application.dto.response.RegisterRentalResponseDto;
import com.flexidorm.artsch.rental_management.application.services.IRentalService;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reservations")
@RestController
@RequestMapping("/api/v1/rental")
public class RentalController {

    private final IRentalService rentalService;

    public RentalController(IRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(summary = "Register a rental")
    @PostMapping("/registerRental")
    public ResponseEntity<ApiResponse<RegisterRentalResponseDto>> registerRental(@Valid @RequestBody RegisterRentalRequestDto request){
        var res = rentalService.registerRental(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Get rentals by student")
    @GetMapping("/getRentalsByStudentId/{student}")
    public ResponseEntity<ApiResponse<List<RegisterRentalResponseDto>>> getRentalsByStudentId(@PathVariable String student){
        var res = rentalService.getRentalsByStudentId(student);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get rentals by student")
    @GetMapping("/getMovimentByStudentId/{student}")
    public ResponseEntity<ApiResponse<List<RegisterRentalResponseDto>>> getMovimentByStudentId(@PathVariable String student){
        var res = rentalService.getMovimentByStudentId(student);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get rentals by arrenderId")
    @GetMapping("/getRentalsByArrenderId/{arrenderId}")
    public ResponseEntity<ApiResponse<List<RegisterRentalResponseDto>>> getRentalsByArrenderId(@PathVariable String arrenderId){
        var res = rentalService.getRentalsByArrenderId(arrenderId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get rentals by arrenderId")
    @GetMapping("/getMovimentByArrenderId/{arrenderId}")
    public ResponseEntity<ApiResponse<List<RegisterRentalResponseDto>>> getMovimentByArrenderId(@PathVariable String arrenderId){
        var res = rentalService.getMovimentByArrenderId(arrenderId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{reservationId}/toggleFavorite")
        public ResponseEntity<ApiResponse<RegisterRentalResponseDto>> toggleFavorite(@PathVariable Long reservationId) {
            var res = rentalService.toggleFavorite(reservationId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

    @PutMapping("/{reservationId}/toggleEndRental")
    public ResponseEntity<ApiResponse<RegisterRentalResponseDto>> toggleEndRental(@PathVariable Long reservationId) {
        var res = rentalService.toggleEndRental(reservationId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/search/{student}")
    public ResponseEntity<ApiResponse<List<RegisterRentalResponseDto>>> findByStudentAndFavorite(
            @PathVariable String student) {

        var res= rentalService.findByStudentAndFavorite(student);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
