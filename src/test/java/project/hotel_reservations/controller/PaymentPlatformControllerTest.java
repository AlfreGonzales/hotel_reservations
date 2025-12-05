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
import project.hotel_reservations.dto.payment_platform.PaymentPlatformCreateDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformResponseDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformUpdateDTO;
import project.hotel_reservations.service.PaymentPlatformService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PaymentPlatformController.class)
public class PaymentPlatformControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentPlatformService service;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID id = UUID.randomUUID();

    @Test
    @DisplayName("Create a payment platform")
    void shouldCreatePaymentPlatform() throws Exception {
        PaymentPlatformCreateDTO req = PaymentPlatformCreateDTO.builder()
                .name("PayPal")
                .code("M45")
                .build();

        PaymentPlatformResponseDTO dto = PaymentPlatformResponseDTO.builder()
                .id(id)
                .name("PayPal")
                .code("M45")
                .build();

        when(service.create(any(PaymentPlatformCreateDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/payment-platforms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("PayPal"));
    }

    @Test
    @DisplayName("Get all payment platforms")
    void shouldFindAllPaymentPlatforms() throws Exception {

        PaymentPlatformResponseDTO dto = PaymentPlatformResponseDTO.builder()
                .id(id)
                .name("PayPal")
                .build();

        when(service.findAll()).thenReturn(List.of(dto));

        mockMvc.perform(get("/payment-platforms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].name").value("PayPal"));
    }

    @Test
    @DisplayName("Get payment platform by id")
    void shouldFindPaymentPlatformById() throws Exception {

        PaymentPlatformResponseDTO dto = PaymentPlatformResponseDTO.builder()
                .id(id)
                .name("PayPal")
                .build();

        when(service.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/payment-platforms/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("PayPal"));
    }

    @Test
    @DisplayName("Update a payment platform")
    void shouldUpdatePaymentPlatform() throws Exception {

        PaymentPlatformUpdateDTO req = PaymentPlatformUpdateDTO.builder()
                .name("PayPal Updated")
                .build();

        PaymentPlatformResponseDTO dto = PaymentPlatformResponseDTO.builder()
                .id(id)
                .name("PayPal Updated")
                .build();

        when(service.update(any(UUID.class), any(PaymentPlatformUpdateDTO.class))).thenReturn(dto);

        mockMvc.perform(patch("/payment-platforms/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("PayPal Updated"));
    }

    @Test
    @DisplayName("Delete a payment platform")
    void shouldDeletePaymentPlatform() throws Exception {

        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/payment-platforms/" + id))
                .andExpect(status().isNoContent());
    }
}
