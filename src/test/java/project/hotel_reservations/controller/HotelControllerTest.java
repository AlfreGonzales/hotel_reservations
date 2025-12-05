package project.hotel_reservations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import project.hotel_reservations.dto.hotel.HotelCreateDTO;
import project.hotel_reservations.dto.hotel.HotelResponseDTO;
import project.hotel_reservations.security.JwtService;
import project.hotel_reservations.service.HotelService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HotelController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HotelControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID id = UUID.randomUUID();

    @Test
    @DisplayName("Create hotel")
    void shouldCreateHotel() throws Exception {
        HotelCreateDTO req = HotelCreateDTO.builder()
                .name("Hotel")
                .address("Calle manuel najar")
                .phone("940020020")
                .email("hotel@mail.com")
                .build();

        HotelResponseDTO dto = HotelResponseDTO.builder()
                .id(id)
                .name("Hotel")
                .address("Calle manuel najar")
                .phone("940020020")
                .email("hotel@mail.com")
                .build();

        when(hotelService.create(any(HotelCreateDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Hotel"));
    }

    @Test
    @DisplayName("Get list of hotels")
    void shouldGetHotels() throws Exception {
        HotelResponseDTO dto = HotelResponseDTO.builder()
                .name("Hotel")
                .build();

        when(hotelService.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Hotel"));
    }

    @Test
    @DisplayName("Get hotel by id")
    void shouldGetHotelById() throws Exception {
        HotelResponseDTO dto = HotelResponseDTO.builder()
                .name("Hotel")
                .build();

        when(hotelService.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/hotels/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hotel"));
    }

    @Test
    @DisplayName("Delete hotel by id")
    void shouldDeleteHotelById() throws Exception {
        doNothing().when(hotelService).delete(id);
        mockMvc.perform(delete("/hotels/{id}", id))
                .andExpect(status().isNoContent());
    }
}
