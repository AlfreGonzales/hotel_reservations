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
import project.hotel_reservations.dto.HotelAdminCreateDTO;
import project.hotel_reservations.dto.HotelAdminResponseDTO;
import project.hotel_reservations.dto.HotelAdminUpdateDTO;
import project.hotel_reservations.service.HotelAdminService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// TODO: remove @AutoConfigureMockMvc(addFilters = false)  and mock jwt
@ExtendWith(SpringExtension.class)
@WebMvcTest(HotelAdminController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HotelAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private HotelAdminService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID id = UUID.randomUUID();

    @Test
    @DisplayName("Create hotel admin")
    void shouldCreateHotelAdmin() throws Exception {
        HotelAdminCreateDTO req = HotelAdminCreateDTO.builder()
                .identification("111")
                .name("Juan")
                .email("juan@gmail.com")
                .position("Supervisor")
                .shift("tarde")
                .build();

        HotelAdminResponseDTO dto = HotelAdminResponseDTO.builder()
                .id(id)
                .identification("111")
                .name("Juan")
                .email("juan@gmail.com")
                .position("Supervisor")
                .shift("tarde")
                .build();

        when(service.create(any(HotelAdminCreateDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/hotel-admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Juan"))
                .andExpect(jsonPath("$.position").value("Supervisor"));
    }

    @Test
    @DisplayName("Return list of hotel admins")
    void shouldReturnHotelAdminsList() throws Exception {
        HotelAdminResponseDTO dto = HotelAdminResponseDTO.builder()
                .name("Usuario")
                .build();

        when(service.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/hotel-admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Usuario"));
    }

    @Test
    @DisplayName("Find hotel admin by ID")
    void shouldFindHotelAdminById() throws Exception {
        HotelAdminResponseDTO dto = HotelAdminResponseDTO.builder()
                .name("Usuario")
                .position("Gerente")
                .build();

        when(service.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/hotel-admins/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Usuario"))
                .andExpect(jsonPath("$.position").value("Gerente"));
    }

    @Test
    @DisplayName("Return 404 when hotel admin not found")
    void shouldReturnNotFound() throws Exception {
        when(service.findById(id)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/hotel-admins/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update hotel admin")
    void shouldUpdateHotelAdmin() throws Exception {
        HotelAdminUpdateDTO req = HotelAdminUpdateDTO.builder()
                .identification("321")
                .name("Nuevo Nombre")
                .email("nuevo@gmail.com")
                .position("Director")
                .shift("noche")
                .build();

        HotelAdminResponseDTO dto = HotelAdminResponseDTO.builder()
                .id(id)
                .identification("321")
                .name("Nuevo Nombre")
                .email("nuevo@gmail.com")
                .position("Director")
                .shift("noche")
                .build();

        when(service.update(eq(id), any(HotelAdminUpdateDTO.class))).thenReturn(dto);

        mockMvc.perform(patch("/hotel-admins/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nuevo Nombre"))
                .andExpect(jsonPath("$.shift").value("noche"));
    }

    @Test
    @DisplayName("Delete hotel admin")
    void shouldDeleteHotelAdmin() throws Exception {
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/hotel-admins/" + id))
                .andExpect(status().isNoContent());
    }
}
