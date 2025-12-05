package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.hotel_reservations.dto.room.RoomCreateDTO;
import project.hotel_reservations.dto.room.RoomResponseDTO;
import project.hotel_reservations.dto.room.RoomUpdateDTO;
import project.hotel_reservations.mapper.RoomMapper;
import project.hotel_reservations.model.Hotel;
import project.hotel_reservations.model.Room;
import project.hotel_reservations.repository.HotelRepository;
import project.hotel_reservations.repository.RoomRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {
    private UUID id;
    private UUID hotelId;
    private Room room;
    private Hotel hotel;

    @Mock
    private RoomRepository repository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomMapper mapper;

    @InjectMocks
    private RoomServiceImpl service;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        hotelId = UUID.randomUUID();

        hotel = Hotel.builder().id(hotelId).build();

        room = Room.builder()
                .id(id)
                .code("A1")
                .capacity(2)
                .type("matrimonial")
                .price(new BigDecimal(500))
                .description("Habitacion con vista a la avenida")
                .build();
    }

    @Test
    @DisplayName("Create room successfully")
    void shouldCreateRoom() {
        RoomCreateDTO req = RoomCreateDTO.builder()
                .code("A1")
                .capacity(2)
                .hotelId(hotelId)
                .build();

        RoomResponseDTO dto = RoomResponseDTO.builder().id(id).build();

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(repository.save(any(Room.class))).thenReturn(room);
        when(mapper.toDto(room)).thenReturn(dto);

        RoomResponseDTO result = service.create(req);

        assertEquals(id, result.id());
        verify(repository).save(any(Room.class));
    }

    @Test
    @DisplayName("Throw when creating room and hotel not found")
    void shouldThrowWhenHotelNotFoundOnCreate() {
        RoomCreateDTO req = RoomCreateDTO.builder()
                .hotelId(hotelId)
                .build();

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.create(req));
    }

    @Test
    @DisplayName("Find room by ID successfully")
    void shouldFindById() {
        RoomResponseDTO dto = RoomResponseDTO.builder().id(id).build();

        when(repository.findById(id)).thenReturn(Optional.of(room));
        when(mapper.toDto(room)).thenReturn(dto);

        RoomResponseDTO result = service.findById(id);

        assertEquals(id, result.id());
    }

    @Test
    @DisplayName("Throw when finding room by ID not found")
    void shouldThrowFindById() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Update room successfully")
    void shouldUpdateRoom() {
        RoomUpdateDTO req = RoomUpdateDTO.builder()
                .description("Nueva descripcion")
                .build();

        RoomResponseDTO dto = RoomResponseDTO.builder().id(id).build();

        when(repository.findById(id)).thenReturn(Optional.of(room));
        doNothing().when(mapper).toEntity(req, room);
        when(repository.save(room)).thenReturn(room);
        when(mapper.toDto(room)).thenReturn(dto);

        RoomResponseDTO result = service.update(id, req);

        assertEquals(id, result.id());
        verify(repository).save(room);
    }

    @Test
    @DisplayName("Throw when updating non-existing room")
    void shouldThrowWhenUpdateNotFound() {
        RoomUpdateDTO req = RoomUpdateDTO.builder().build();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(id, req));
    }

    @Test
    @DisplayName("Soft delete room successfully")
    void shouldSoftDeleteRoom() {
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        service.softDelete(id);

        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Throw when soft deleting non-existing room")
    void shouldThrowWhenDeleteNotFound() {
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.softDelete(id));
    }

    @Test
    @DisplayName("Find all deleted rooms")
    void shouldFindAllDeleted() {
        when(repository.findAllDeleted()).thenReturn(List.of(room));
        when(mapper.toDto(room)).thenReturn(RoomResponseDTO.builder().build());

        List<RoomResponseDTO> result = service.findAllDeleted();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Find rooms by hotel ID")
    void shouldFindByHotelId() {
        when(hotelRepository.existsById(hotelId)).thenReturn(true);
        when(repository.findByHotelId(hotelId)).thenReturn(List.of(room));
        when(mapper.toDto(room)).thenReturn(RoomResponseDTO.builder().build());

        List<RoomResponseDTO> result = service.findByHotelId(hotelId);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Throw when hotel not found on findByHotelId")
    void shouldThrowHotelNotFoundOnFindByHotelId() {
        when(hotelRepository.existsById(hotelId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> service.findByHotelId(hotelId));
    }

    @Test
    @DisplayName("Find available rooms")
    void shouldFindAvailableRooms() {
        when(hotelRepository.existsById(hotelId)).thenReturn(true);
        when(repository.findAvailableRooms(hotelId)).thenReturn(List.of(room));
        when(mapper.toDto(room)).thenReturn(RoomResponseDTO.builder().build());

        List<RoomResponseDTO> result = service.findAvailableRooms(hotelId);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Throw when hotel not found on findAvailableRooms")
    void shouldThrowHotelNotFoundOnFindAvailableRooms() {
        when(hotelRepository.existsById(hotelId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> service.findAvailableRooms(hotelId));
    }
}
