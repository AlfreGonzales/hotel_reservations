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
import project.hotel_reservations.dto.HotelAdminCreateDTO;
import project.hotel_reservations.dto.HotelAdminResponseDTO;
import project.hotel_reservations.dto.HotelAdminUpdateDTO;
import project.hotel_reservations.mapper.HotelAdminMapper;
import project.hotel_reservations.model.HotelAdmin;
import project.hotel_reservations.repository.HotelAdminRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelAdminServiceImplTest {

    private UUID id;
    private HotelAdmin hotelAdmin;

    @Mock
    private HotelAdminRepository repository;

    @Mock
    private HotelAdminMapper mapper;

    @InjectMocks
    private HotelAdminServiceImpl service;

    @BeforeEach
    void init() {
        id = UUID.randomUUID();

        hotelAdmin = HotelAdmin.builder()
                .id(id)
                .identification("123")
                .name("Usuario")
                .email("usuario@gmail.com")
                .position("Gerente")
                .shift("mañana")
                .build();
    }

    @AfterEach
    void tearDown() {
        hotelAdmin = null;
    }

    @Test
    @DisplayName("Create hotel admin successfully")
    void shouldCreateHotelAdmin() {
        HotelAdminCreateDTO req = new HotelAdminCreateDTO(
                "123", "Juan", "juan@gmail.com", "Supervisor", "tarde"
        );

        HotelAdminResponseDTO dto = new HotelAdminResponseDTO(
                id, "123", "Juan", "juan@mail.com", "Supervisor", "tarde", null, null
        );

        when(repository.save(any(HotelAdmin.class))).thenReturn(hotelAdmin);
        when(mapper.toDto(hotelAdmin)).thenReturn(dto);

        HotelAdminResponseDTO result = service.create(req);

        assertEquals(dto.name(), result.name());
        assertEquals(dto.email(), result.email());

        verify(repository).save(any(HotelAdmin.class));
        verify(mapper).toDto(hotelAdmin);
    }

    @Test
    @DisplayName("List all hotel admins")
    void shouldReturnListOfHotelAdmins() {
        when(repository.findAll()).thenReturn(List.of(hotelAdmin));
        when(mapper.toDto(hotelAdmin)).thenReturn(
                new HotelAdminResponseDTO(null, null, null, null, null, null, null, null)
        );

        List<HotelAdminResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Find hotel admin by ID successfully")
    void shouldFindById() {
        HotelAdminResponseDTO dto = new HotelAdminResponseDTO(id, "123", null, null, null, null, null, null);

        when(repository.findById(id)).thenReturn(Optional.of(hotelAdmin));
        when(mapper.toDto(hotelAdmin)).thenReturn(dto);

        HotelAdminResponseDTO result = service.findById(id);

        assertEquals(id, result.id());
        assertEquals("123", result.identification());
    }

    @Test
    @DisplayName("Throw exception when hotel admin not found by ID")
    void shouldThrowWhenFindByIdNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Update hotel admin successfully")
    void shouldUpdateHotelAdmin() {
        HotelAdminUpdateDTO req = new HotelAdminUpdateDTO(
                "321", "Nuevo Nombre", "nuevo@gmail.com", "Director", "tarde"
        );

        HotelAdmin updated = HotelAdmin.builder()
                .id(id)
                .identification("321")
                .name("Nuevo Nombre")
                .email("nuevo@gmail.com")
                .position("Director")
                .shift("tarde")
                .build();

        HotelAdminResponseDTO dto = new HotelAdminResponseDTO(
                id, "321", "Nuevo Nombre", "nuevo@gmail.com", "Director", "tarde", null, null
        );

        when(repository.findById(id)).thenReturn(Optional.of(hotelAdmin));
        doAnswer(inv -> {
            HotelAdminUpdateDTO r = inv.getArgument(0);
            HotelAdmin e = inv.getArgument(1);

            e.setIdentification(r.identification());
            e.setName(r.name());
            e.setEmail(r.email());
            e.setPosition(r.position());
            e.setShift(r.shift());
            return null;
        }).when(mapper).toEntity(req, hotelAdmin);

        when(repository.save(hotelAdmin)).thenReturn(updated);
        when(mapper.toDto(updated)).thenReturn(dto);

        HotelAdminResponseDTO result = service.update(id, req);

        assertEquals("Nuevo Nombre", result.name());
        verify(repository).save(hotelAdmin);
    }

    @Test
    @DisplayName("Throw when updating non-existing hotel admin")
    void shouldThrowWhenUpdateNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        HotelAdminUpdateDTO req = new HotelAdminUpdateDTO(null, null, null, null, null);

        assertThrows(EntityNotFoundException.class, () -> service.update(id, req));
    }

    @Test
    @DisplayName("Delete hotel admin successfully")
    void shouldDeleteHotelAdmin() {
        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository).deleteById(id);
    }
}
