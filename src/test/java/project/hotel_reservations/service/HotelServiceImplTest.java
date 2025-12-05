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
import project.hotel_reservations.dto.hotel.HotelCreateDTO;
import project.hotel_reservations.dto.hotel.HotelResponseDTO;
import project.hotel_reservations.dto.hotel.HotelUpdateDTO;
import project.hotel_reservations.mapper.HotelMapper;
import project.hotel_reservations.model.Hotel;
import project.hotel_reservations.repository.HotelRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {
    private UUID id;
    private Hotel hotel;

    @Mock
    private HotelRepository repository;

    @Mock
    private HotelMapper mapper;

    @InjectMocks
    private HotelServiceImpl service;

    @BeforeEach
    void init() {
        id = UUID.randomUUID();

        hotel = Hotel.builder()
                .id(id)
                .name("Hotel California")
                .address("Jiron Bolivia")
                .phone("97772222")
                .email("california@mail.com")
                .build();
    }

    @AfterEach
    void tearDown() {
        hotel = null;
    }

    @Test
    @DisplayName("Create hotel successfully")
    void shouldCreateHotelSuccessfully() {
        HotelCreateDTO req = new HotelCreateDTO("Hotel California", "Jiron Bolivia", "9777222", "california@mail.com");
        HotelResponseDTO dto = new HotelResponseDTO( id, "Hotel California", "Jiron Bolivia", "9777222", "california@mail.com");

        when(repository.save(any(Hotel.class))).thenReturn(hotel);
        when(mapper.toDto(hotel)).thenReturn(dto);

        HotelResponseDTO result = service.create(req);

        assertEquals(dto.name(), result.name());
        assertEquals(dto.address(), result.address());
        assertEquals(dto.phone(), result.phone());
        assertEquals(dto.email(), result.email());

        verify(repository).save(any(Hotel.class));
        verify(mapper).toDto(hotel);
    }

    @Test
    @DisplayName("List all hotels")
    void shouldListAllHotels() {
        when(repository.findAll()).thenReturn(List.of(hotel));
        when(mapper.toDto(hotel)).thenReturn(
                new HotelResponseDTO(null, null, null, null, null)
        );

        List<HotelResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Find hotel by ID successfully")
    void shouldFindHotelById() {
        HotelResponseDTO dto =
                new HotelResponseDTO(id, "Hotel California", "Jiron Bolivia", "97772222", "california@mail.com");

        when(repository.findById(id)).thenReturn(Optional.of(hotel));
        when(mapper.toDto(hotel)).thenReturn(dto);

        HotelResponseDTO result = service.findById(id);

        assertEquals(id, result.id());
        assertEquals("Hotel California", result.name());
    }

    @Test
    @DisplayName("Throw exception when hotel not found by ID")
    void shouldThrowWhenFindByIdNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Update hotel successfully")
    void shouldUpdateHotel() {
        HotelUpdateDTO req =
                new HotelUpdateDTO("Nuevo Hotel", "Nueva Calle", "99999", "nuevo@mail.com");

        Hotel updated = Hotel.builder()
                .id(id)
                .name("Nuevo Hotel")
                .address("Nueva Calle")
                .phone("99999")
                .email("nuevo@mail.com")
                .build();

        HotelResponseDTO dto =
                new HotelResponseDTO(id, "Nuevo Hotel", "Nueva Calle", "99999", "nuevo@mail.com");

        when(repository.findById(id)).thenReturn(Optional.of(hotel));

        // Simulate mapper.toEntity(updateDto, hotel)
        doAnswer(inv -> {
            HotelUpdateDTO r = inv.getArgument(0);
            Hotel h = inv.getArgument(1);
            h.setName(r.name());
            h.setAddress(r.address());
            h.setPhone(r.phone());
            h.setEmail(r.email());
            return null;
        }).when(mapper).toEntity(req, hotel);

        when(repository.save(hotel)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(dto);

        HotelResponseDTO result = service.update(id, req);

        assertEquals("Nuevo Hotel", result.name());
        verify(repository).save(hotel);
    }

    @Test
    @DisplayName("Throw exception when updating non-existing hotel")
    void shouldThrowWhenUpdateNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        HotelUpdateDTO req = new HotelUpdateDTO(null, null, null, null);

        assertThrows(EntityNotFoundException.class, () -> service.update(id, req));
    }

    @Test
    @DisplayName("Delete hotel successfully")
    void shouldDeleteHotel() {
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Throw when deleting non-existing hotel")
    void shouldThrowWhenDeletingNotFound() {
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.delete(id));
    }
}
