package project.hotel_reservations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import project.hotel_reservations.dto.room.RoomCreateDTO;
import project.hotel_reservations.dto.room.RoomResponseDTO;
import project.hotel_reservations.dto.room.RoomUpdateDTO;
import project.hotel_reservations.service.RoomService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
public class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID id = UUID.randomUUID();
    private final UUID hotelId = UUID.randomUUID();

    @Test
    @DisplayName("Create a room")
    void shouldCreateRoom() throws Exception {
        RoomCreateDTO req = RoomCreateDTO.builder()
                .code("12")
                .capacity(3)
                .type("doble")
                .price(new BigDecimal("12"))
                .description("Como en casa")
                .hotelId(hotelId)
                .build();

        RoomResponseDTO dto = RoomResponseDTO.builder()
                .id(id)
                .code("12")
                .capacity(3)
                .type("doble")
                .price(new BigDecimal("12"))
                .description("Como en casa")
                .hotelId(hotelId)
                .build();

        when(roomService.create(any(RoomCreateDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code").value("12"));
    }

    @Test
    @DisplayName("Get all rooms")
    void shouldFindAllRooms() throws Exception {
        RoomResponseDTO dto = RoomResponseDTO.builder()
                .id(id)
                .code("12")
                .capacity(3)
                .type("doble")
                .price(new BigDecimal("12"))
                .description("Como en casa")
                .hotelId(hotelId)
                .build();

        when(roomService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].code").value("12"));
    }

    @Test
    @DisplayName("Get room by id")
    void shouldFindRoomById() throws Exception {
        RoomResponseDTO dto = RoomResponseDTO.builder()
                .id(id)
                .code("12")
                .capacity(3)
                .type("doble")
                .price(new BigDecimal("12"))
                .description("Como en casa")
                .hotelId(hotelId)
                .build();

        when(roomService.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/rooms/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.code").value("12"));
    }

    @Test
    @DisplayName("Update a room")
    void shouldUpdateRoom() throws Exception {
        RoomUpdateDTO req = RoomUpdateDTO.builder()
                .code("15")
                .capacity(4)
                .type("suite")
                .price(new BigDecimal("20"))
                .description("Updated room")
                .build();

        RoomResponseDTO dto = RoomResponseDTO.builder()
                .id(id)
                .code("15")
                .capacity(4)
                .type("suite")
                .price(new BigDecimal("20"))
                .description("Updated room")
                .hotelId(hotelId)
                .build();

        when(roomService.update(any(UUID.class), any(RoomUpdateDTO.class))).thenReturn(dto);

        mockMvc.perform(patch("/rooms/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.code").value("15"));
    }

    @Test
    @DisplayName("Delete a room")
    void shouldDeleteRoom() throws Exception {
        doNothing().when(roomService).softDelete(id);

        mockMvc.perform(delete("/rooms/" + id))
                .andExpect(status().isNoContent());
    }
}
