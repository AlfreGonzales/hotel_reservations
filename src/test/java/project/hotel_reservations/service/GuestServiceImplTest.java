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
import project.hotel_reservations.dto.*;
import project.hotel_reservations.mapper.GuestMapper;
import project.hotel_reservations.model.Guest;
import project.hotel_reservations.repository.GuestRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTest {

    private UUID id;
    private Guest guest;

    @Mock
    private GuestRepository repository;

    @Mock
    private GuestMapper mapper;

    @InjectMocks
    private GuestServiceImpl service;

    @BeforeEach
    void init() {
        id = UUID.randomUUID();

        guest = Guest.builder()
                .id(id)
                .identification("123")
                .name("Usuario")
                .email("usuario@gmail.com")
                .birthDate(LocalDate.of(2000, 1, 1))
                .nationality("paraguaya")
                .build();
    }

    @AfterEach
    void tearDown() {
        guest = null;
    }

    @Test
    @DisplayName("Create guest successfully")
    void shouldCreateGuest() {
        GuestCreateDTO req = new GuestCreateDTO(
                "123", "Juan", "juan@gmail.com", LocalDate.of(1990, 1, 1), "chilena"
        );

        GuestResponseDTO dto = new GuestResponseDTO(
                id, "123", "Juan", "juan@gmail.com", LocalDate.of(1990, 1, 1), "chilena", null, null
        );

        when(repository.save(any(Guest.class))).thenReturn(guest);
        when(mapper.toDto(guest)).thenReturn(dto);

        GuestResponseDTO result = service.create(req);

        assertEquals(dto.name(), result.name());
        assertEquals(dto.email(), result.email());

        verify(repository).save(any(Guest.class));
        verify(mapper).toDto(guest);
    }

    @Test
    @DisplayName("List all guests")
    void shouldReturnListOfGuests() {
        when(repository.findAll()).thenReturn(List.of(guest));
        when(mapper.toDto(guest)).thenReturn(
                new GuestResponseDTO(null, null, null, null, null, null, null, null)
        );

        List<GuestResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Find guest by ID successfully")
    void shouldFindById() {
        GuestResponseDTO dto = new GuestResponseDTO(id, "123", null, null, null, null, null, null);

        when(repository.findById(id)).thenReturn(Optional.of(guest));
        when(mapper.toDto(guest)).thenReturn(dto);

        GuestResponseDTO result = service.findById(id);

        assertEquals(id, result.id());
        assertEquals("123", result.identification());
    }

    @Test
    @DisplayName("Throw exception when guest not found by ID")
    void shouldThrowWhenFindByIdNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Update guest successfully")
    void shouldUpdateGuest() {
        GuestUpdateDTO req = new GuestUpdateDTO(
                "321", "Nuevo Nombre", "nuevo@gmail.com", LocalDate.of(1990, 1, 1), "chilena"
        );

        Guest updated = Guest.builder()
                .id(id)
                .identification("321")
                .name("Nuevo Nombre")
                .email("nuevo@gmail.com")
                .birthDate(LocalDate.of(1990, 1, 1))
                .nationality("chilena")
                .build();

        GuestResponseDTO dto = new GuestResponseDTO(
                id, "321", "Nuevo Nombre", "nuevo@gmail.com", LocalDate.of(1990, 1, 1), "chilena", null, null
        );

        when(repository.findById(id)).thenReturn(Optional.of(guest));
        doAnswer(inv -> {
            GuestUpdateDTO r = inv.getArgument(0);
            Guest e = inv.getArgument(1);

            e.setIdentification(r.identification());
            e.setName(r.name());
            e.setEmail(r.email());
            e.setBirthDate(r.birthDate());
            e.setNationality(r.nationality());
            return null;
        }).when(mapper).toEntity(req, guest);

        when(repository.save(guest)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(dto);

        GuestResponseDTO result = service.update(id, req);

        assertEquals("Nuevo Nombre", result.name());
        verify(repository).save(guest);
    }

    @Test
    @DisplayName("Throw when updating non-existing guest")
    void shouldThrowWhenUpdateNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        GuestUpdateDTO req = new GuestUpdateDTO(null, null, null, null, null);

        assertThrows(EntityNotFoundException.class, () -> service.update(id, req));
    }

    @Test
    @DisplayName("Delete guest successfully")
    void shouldDeleteGuest() {
        when(repository.existsById(id)).thenReturn(true);

        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository).deleteById(id);
    }
}
