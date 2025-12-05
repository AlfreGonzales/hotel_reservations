package project.hotel_reservations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import project.hotel_reservations.dto.guest.GuestCreateDTO;
import project.hotel_reservations.dto.guest.GuestResponseDTO;
import project.hotel_reservations.dto.guest.GuestUpdateDTO;
import project.hotel_reservations.service.GuestService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GuestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private GuestService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID id = UUID.randomUUID();

    @Test
    @DisplayName("Create guest")
    void shouldCreateGuest() throws Exception {
        GuestCreateDTO req = new GuestCreateDTO(
                "123", "Juan", "juan@gmail.com", LocalDate.of(1990, 1, 1), "chilena"
        );

        GuestResponseDTO dto =  new GuestResponseDTO(
                id, "123", "Juan", "juan@gmail.com", LocalDate.of(1990, 1, 1), "chilena", null, null
        );

        when(service.create(any(GuestCreateDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@gmail.com"));
    }

    @Test
    @DisplayName("Return list of guests")
    void shouldReturnGuestsList() throws Exception {
        GuestResponseDTO dto = new GuestResponseDTO(
                id, null, "Juan", null, null, null, null, null
        );

        when(service.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/guests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Juan"));
    }

    @Test
    @DisplayName("Find guest by ID")
    void shouldFindGuestById() throws Exception {
        GuestResponseDTO dto = new GuestResponseDTO(
                id, null, "Juan", null, null, null, null, null
        );

        when(service.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/guests/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Juan"));
    }

    @Test
    @DisplayName("Return 404 when guest not found")
    void shouldReturnNotFound() throws Exception {
        when(service.findById(id)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/guests/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update guest")
    void shouldUpdateHotelGuest() throws Exception {
        GuestUpdateDTO req = new GuestUpdateDTO(
                "321", "Nuevo Nombre", "nuevo@gmail.com", LocalDate.of(1990, 1, 1), "chilena"
        );

        GuestResponseDTO dto = new GuestResponseDTO(
                id, "321", "Nuevo Nombre", "nuevo@gmail.com", LocalDate.of(1990, 1, 1), "chilena", null, null
        );

        when(service.update(eq(id), any(GuestUpdateDTO.class))).thenReturn(dto);

        mockMvc.perform(patch("/guests/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate").value("1990-01-01"))
                .andExpect(jsonPath("$.nationality").value("chilena"));
    }

    @Test
    @DisplayName("Delete guest")
    void shouldDeleteGuest() throws Exception {
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/guests/" + id))
                .andExpect(status().isNoContent());
    }
}
