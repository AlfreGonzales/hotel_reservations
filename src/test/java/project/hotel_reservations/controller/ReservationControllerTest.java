package project.hotel_reservations.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import project.hotel_reservations.dto.reservation.PayReservationDTO;
import project.hotel_reservations.dto.reservation.ReservationCreateDTO;
import project.hotel_reservations.dto.reservation.ReservationResponseDTO;
import project.hotel_reservations.model.PaymentMethod;
import project.hotel_reservations.service.ReservationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReservationService service;

    @Test
    @DisplayName("POST /reservations → should create a reservation and return 201")
    void create_reservation_shouldReturn201() throws Exception {

        ReservationCreateDTO request = new ReservationCreateDTO(
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
                2,
                UUID.randomUUID(),
                UUID.randomUUID()
        );

        ReservationResponseDTO response = ReservationResponseDTO.builder()
                .id(UUID.randomUUID())
                .peopleCount(2)
                .build();

        when(service.create(any())).thenReturn(response);

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.peopleCount").value(2));
    }

    @Test
    @DisplayName("GET /reservations → should return list of reservations with 200")
    void findAll_shouldReturn200_andList() throws Exception {

        ReservationResponseDTO reservation = ReservationResponseDTO.builder()
                .id(UUID.randomUUID())
                .build();

        when(service.findAll()).thenReturn(List.of(reservation));

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /reservations/{id} → should return reservation with 200")
    void findById_shouldReturn200() throws Exception {

        UUID id = UUID.randomUUID();

        ReservationResponseDTO dto = ReservationResponseDTO.builder()
                .id(id)
                .build();

        when(service.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/reservations/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    @DisplayName("PATCH /reservations/{id}/confirm → should confirm reservation and return 200")
    void confirmReservation_shouldReturn200() throws Exception {

        UUID id = UUID.randomUUID();

        PayReservationDTO request = PayReservationDTO.builder()
                .totalAmount(new BigDecimal("150.00"))
                .paymentMethod(PaymentMethod.CASH)
                .paymentPlatformId(UUID.randomUUID())
                .build();

        ReservationResponseDTO response = ReservationResponseDTO.builder()
                .id(id)
                .build();

        when(service.confirmReservation(eq(id), any())).thenReturn(response);

        mockMvc.perform(patch("/reservations/{id}/confirm", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());
    }
}
