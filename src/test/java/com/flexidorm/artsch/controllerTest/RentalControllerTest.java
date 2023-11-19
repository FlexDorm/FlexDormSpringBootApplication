package com.flexidorm.artsch.controllerTest;

import com.flexidorm.artsch.rental_management.application.controllers.RentalController;
import com.flexidorm.artsch.rental_management.application.dto.request.RegisterRentalRequestDto;
import com.flexidorm.artsch.rental_management.application.dto.response.RegisterRentalResponseDto;
import com.flexidorm.artsch.rental_management.application.services.IRentalService;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import com.flexidorm.artsch.shared.model.enums.EStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Disabled;

@Disabled("For deployment")
@SpringBootTest
@AutoConfigureMockMvc
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private RentalController rentalController;

    @Mock
    private IRentalService rentalService;

    /*@Test
    void testRegisterRental() throws Exception {
        // Define a RegisterRentalRequestDto
        RegisterRentalRequestDto request = new RegisterRentalRequestDto();

        // Define a RegisterRentalResponseDto
        RegisterRentalResponseDto response = new RegisterRentalResponseDto();

        // Define una instancia de ApiResponse que contiene RegisterRentalResponseDto
                ApiResponse<RegisterRentalResponseDto> apiResponse = new ApiResponse<>("Rental was successfully registered", EStatus.SUCCESS, response);

        // Mock el servicio para que devuelva el ApiResponse
                when(rentalService.registerRental(request)).thenReturn(apiResponse);


        // Perform a POST request to the controller endpoint
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rental/registerRental")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"date\":\"2023-02-20\",\n" +
                                "    \"email\":\"samm85299@gmail.com\",\n" +
                                "    \"totalPrice\":36,\n" +
                                "    \"hourFinal\":\"23:00\",\n" +
                                "    \"hourInit\":\"20:00\",\n" +
                                "    \"imageUrl\":\"adsasd\",\n" +
                                "    \"observation\":\"ninguna\",\n" +
                                "    \"phone\":\"924773421\",\n" +
                                "    \"room\":1,\n" +
                                "    \"student\":1\n" +
                                "}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }*/
    
    @Test
    void testGetRentalsByStudentId() throws Exception {
        // Definir el ID de estudiante para la prueba
        String studentId = "1"; // Ajusta el valor del ID de estudiante según tus necesidades

        // Definir una lista de RegisterRentalResponseDto que se espera como resultado
        List<RegisterRentalResponseDto> rentalList = new ArrayList<>();
        // Agregar elementos a la lista según tus necesidades

        // Crear un ApiResponse que contiene la lista de RegisterRentalResponseDto
        ApiResponse<List<RegisterRentalResponseDto>> apiResponse = new ApiResponse<>("Rentals retrieved successfully", EStatus.SUCCESS, rentalList);

        // Mockear el servicio para que devuelva el ApiResponse
        when(rentalService.getRentalsByStudentId(studentId)).thenReturn(apiResponse);

        // Realizar una solicitud GET al controlador endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/rental/getRentalsByStudentId/{student}", studentId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

}
