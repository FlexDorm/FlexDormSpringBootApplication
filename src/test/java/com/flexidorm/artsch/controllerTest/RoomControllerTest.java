package com.flexidorm.artsch.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexidorm.artsch.rental_management.application.dto.request.RegisterRoomRequestDto;
import com.flexidorm.artsch.rental_management.application.dto.response.RegisterRoomResponseDto;
import com.flexidorm.artsch.rental_management.application.services.IRoomService;
import com.flexidorm.artsch.rental_management.application.services.impl.RoomService;
import com.flexidorm.artsch.shared.model.dto.response.ApiResponse;
import com.flexidorm.artsch.shared.model.enums.EStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IRoomService roomService;

    @Test
    void testRegisterRoom() throws Exception {
        // Crear una instancia válida de RegisterRoomRequestDto
        RegisterRoomRequestDto request = new RegisterRoomRequestDto();
        request.setTitle("123");
        request.setDescription("casanueva");
        request.setAddress("lima peru");
        request.setImageUrl("asdasdasd");
        request.setPrice(12);
        request.setNearUniversities("UPC");
        request.setArrenderId(2L);

        RegisterRoomResponseDto response = new RegisterRoomResponseDto();
        ApiResponse<RegisterRoomResponseDto> apiResponse = new ApiResponse<>("Room was successfully registered", EStatus.SUCCESS, response);

        // Mockear el servicio para que retorne la respuesta esperada
        IRoomService roomService = Mockito.mock(IRoomService.class);
        when(roomService.registerRoom(request)).thenReturn(apiResponse);

        // Realizar la solicitud POST al endpoint del controlador
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/room/registerRoom")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request))) // Convierte el objeto a JSON
                .andExpect(status().isCreated());
    }

    // Método para convertir un objeto a JSON
    private String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetRoomsByRenderId() throws Exception {
        // Preparar datos de prueba
        Long arrenderId = 2L; // ID de arrender de prueba
        List<RegisterRoomResponseDto> roomList = new ArrayList<>(); // Lista de habitaciones de prueba
        // Agregar elementos a la lista, si es necesario

        ApiResponse<List<RegisterRoomResponseDto>> apiResponse = new ApiResponse<>("Rooms retrieved successfully", EStatus.SUCCESS, roomList);

        // Crear un objeto mock de RoomService
        RoomService roomService = Mockito.mock(RoomService.class);

        // Configurar el comportamiento del método getRoomsByRenderId en el objeto mock
        when(roomService.getRoomsByRenderId(eq(arrenderId))).thenReturn(apiResponse);

        // Realizar la solicitud GET al controlador
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/getRoomsByArrenderId/{arrenderId}", arrenderId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetRoomsByState() throws Exception {
        String status = "free"; // Ajusta el estado

        // Realizar la solicitud GET al controlador
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/getRoomsByStatusId/{status}", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetRoomById() throws Exception {
        Long roomId = 1L; // Ajusta el ID de la habitación

        // Realizar la solicitud GET al controlador
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/room/getRoomsByRoomId/{roomId}", roomId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
}
