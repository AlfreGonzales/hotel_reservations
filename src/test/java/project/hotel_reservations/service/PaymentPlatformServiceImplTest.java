package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformCreateDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformResponseDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformUpdateDTO;
import project.hotel_reservations.mapper.PaymentPlatformMapper;
import project.hotel_reservations.model.PaymentPlatform;
import project.hotel_reservations.repository.PaymentPlatformRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentPlatformServiceImplTest {
    private UUID id;
    private PaymentPlatform platform;

    @Mock
    private PaymentPlatformRepository repository;

    @Mock
    private PaymentPlatformMapper mapper;

    @InjectMocks
    private PaymentPlatformServiceImpl service;

    @BeforeEach
    void init() {
        id = UUID.randomUUID();

        platform = PaymentPlatform.builder()
                .id(id)
                .name("Hotel California")
                .code("HR4")
                .build();
    }

    @AfterEach
    void tearDown() {
        platform = null;
    }

    @Test
    @DisplayName("Create payment platform successfully")
    void shouldCreatePlatform() {
        PaymentPlatformCreateDTO req =
                PaymentPlatformCreateDTO.builder()
                        .name("PayPal")
                        .code("PP")
                        .build();

        PaymentPlatformResponseDTO dto =
                PaymentPlatformResponseDTO.builder()
                        .id(id)
                        .name("PayPal")
                        .code("PP")
                        .build();

        when(repository.save(any(PaymentPlatform.class))).thenReturn(platform);
        when(mapper.toDto(platform)).thenReturn(dto);

        PaymentPlatformResponseDTO result = service.create(req);

        assertEquals("PayPal", result.name());
        assertEquals("PP", result.code());

        verify(repository).save(any(PaymentPlatform.class));
        verify(mapper).toDto(platform);
    }

    @Test
    @DisplayName("List all payment platforms")
    void shouldListAllPlatforms() {
        when(repository.findAll()).thenReturn(List.of(platform));
        when(mapper.toDto(platform)).thenReturn(
                PaymentPlatformResponseDTO.builder()
                        .id(null)
                        .name(null)
                        .code(null)
                        .build()
        );

        List<PaymentPlatformResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Find platform by ID successfully")
    void shouldFindById() {
        PaymentPlatformResponseDTO dto =
                PaymentPlatformResponseDTO.builder()
                        .id(id)
                        .name("PayPal")
                        .code("PP")
                        .build();

        when(repository.findById(id)).thenReturn(Optional.of(platform));
        when(mapper.toDto(platform)).thenReturn(dto);

        PaymentPlatformResponseDTO result = service.findById(id);

        assertEquals(id, result.id());
        assertEquals("PayPal", result.name());
    }

    @Test
    @DisplayName("Throw exception when platform not found by ID")
    void shouldThrowWhenFindByIdNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Update payment platform successfully")
    void shouldUpdatePlatform() {
        PaymentPlatformUpdateDTO req =
                PaymentPlatformUpdateDTO.builder()
                        .name("PayPal Updated")
                        .code("PPU")
                        .active(false)
                        .build();

        PaymentPlatform updated = PaymentPlatform.builder()
                .id(id)
                .name("PayPal Updated")
                .code("PPU")
                .build();

        PaymentPlatformResponseDTO dto =
                PaymentPlatformResponseDTO.builder()
                        .id(id)
                        .name("PayPal Updated")
                        .code("PPU")
                        .build();

        when(repository.findById(id)).thenReturn(Optional.of(platform));

        // Simulate mapper.toEntity(updateDTO, entity)
        doAnswer(inv -> {
            PaymentPlatformUpdateDTO r = inv.getArgument(0);
            PaymentPlatform e = inv.getArgument(1);
            e.setName(r.name());
            e.setCode(r.code());
            return null;
        }).when(mapper).toEntity(req, platform);

        when(repository.save(platform)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(dto);

        PaymentPlatformResponseDTO result = service.update(id, req);

        assertEquals("PayPal Updated", result.name());
        verify(repository).save(platform);
    }

    @Test
    @DisplayName("Throw when updating non-existing platform")
    void shouldThrowWhenUpdateNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        PaymentPlatformUpdateDTO req =
                PaymentPlatformUpdateDTO.builder()
                        .name(null)
                        .code(null)
                        .build();

        assertThrows(EntityNotFoundException.class, () -> service.update(id, req));
    }

    @Test
    @DisplayName("Delete payment platform successfully")
    void shouldDeletePlatform() {
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Throw when deleting non-existing platform")
    void shouldThrowWhenDeletingNotFound() {
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.delete(id));
    }
}
