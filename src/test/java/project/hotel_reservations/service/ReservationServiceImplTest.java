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
import project.hotel_reservations.dto.payment.PaymentCreateDTO;
import project.hotel_reservations.dto.reservation.PayReservationDTO;
import project.hotel_reservations.dto.reservation.ReservationCreateDTO;
import project.hotel_reservations.dto.reservation.ReservationResponseDTO;
import project.hotel_reservations.exception.ReservationNotFoundException;
import project.hotel_reservations.mapper.ReservationMapper;
import project.hotel_reservations.model.*;
import project.hotel_reservations.repository.GuestRepository;
import project.hotel_reservations.repository.ReservationRepository;
import project.hotel_reservations.repository.RoomRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImplTest {

    private UUID id;
    private Reservation reservation;
    private Room room;
    private Guest guest;

    @Mock
    private ReservationRepository repository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private ReservationMapper mapper;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private ReservationServiceImpl service;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();

        room = Room.builder().id(UUID.randomUUID()).build();
        guest = Guest.builder().id(UUID.randomUUID()).build();

        reservation = Reservation.builder()
                .id(id)
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(1))
                .peopleCount(2)
                .status(ReservationStatus.PENDING)
                .build();
    }

    @Test
    @DisplayName("Create reservation successfully")
    void shouldCreateReservation() {
        ReservationCreateDTO req = ReservationCreateDTO.builder()
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.now().plusDays(1))
                .peopleCount(2)
                .roomId(room.getId())
                .guestId(guest.getId())
                .build();

        ReservationResponseDTO dto = ReservationResponseDTO.builder().id(id).build();

        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(guestRepository.findById(guest.getId())).thenReturn(Optional.of(guest));
        when(repository.save(any(Reservation.class))).thenReturn(reservation);
        when(mapper.toDto(reservation)).thenReturn(dto);

        ReservationResponseDTO result = service.create(req);

        assertEquals(id, result.id());
        verify(repository).save(any(Reservation.class));
    }

    @Test
    @DisplayName("Throw when room not found on create")
    void shouldThrowWhenRoomNotFound() {
        ReservationCreateDTO req = ReservationCreateDTO.builder()
                .roomId(room.getId())
                .guestId(guest.getId())
                .build();

        when(roomRepository.findById(room.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.create(req));
    }

    @Test
    @DisplayName("Throw when guest not found on create")
    void shouldThrowWhenGuestNotFound() {
        ReservationCreateDTO req = ReservationCreateDTO.builder()
                .roomId(room.getId())
                .guestId(guest.getId())
                .build();

        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(guestRepository.findById(guest.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.create(req));
    }

    @Test
    @DisplayName("Find reservation by ID successfully")
    void shouldFindById() {
        ReservationResponseDTO dto = ReservationResponseDTO.builder().id(id).build();

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(mapper.toDto(reservation)).thenReturn(dto);

        ReservationResponseDTO result = service.findById(id);

        assertEquals(id, result.id());
    }

    @Test
    @DisplayName("Throw when reservation not found by ID")
    void shouldThrowFindById() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Confirm a reservation")
    void shouldConfirmReservation() {
        PayReservationDTO req = PayReservationDTO.builder()
                .totalAmount(new BigDecimal(100.0))
                .paymentMethod(PaymentMethod.TRANSFER)
                .paymentPlatformId(id)
                .build();

        Payment payment = Payment.builder().id(UUID.randomUUID()).build();
        ReservationResponseDTO dto = ReservationResponseDTO.builder().id(id).build();

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(paymentService.processPayment(any(PaymentCreateDTO.class))).thenReturn(payment);
        when(repository.save(reservation)).thenReturn(reservation);
        when(mapper.toDto(reservation)).thenReturn(dto);

        ReservationResponseDTO result = service.confirmReservation(id, req);

        assertEquals(id, result.id());
        assertNotNull(reservation.getPayment());
    }

    @Test
    @DisplayName("Throw when confirming non-existing reservation")
    void shouldThrowWhenConfirmNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class,
                () -> service.confirmReservation(id, PayReservationDTO.builder().build()));
    }

    @Test
    @DisplayName("Cancel reservation successfully")
    void shouldCancelReservation() {
        ReservationResponseDTO dto = ReservationResponseDTO.builder().id(id).build();

        when(repository.findById(id)).thenReturn(Optional.of(reservation));
        when(repository.save(reservation)).thenReturn(reservation);
        when(mapper.toDto(reservation)).thenReturn(dto);

        ReservationResponseDTO result = service.cancelReservation(id);

        assertEquals(id, result.id());
    }

    @Test
    @DisplayName("Throw when canceling non-existing reservation")
    void shouldThrowWhenCancelNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> service.cancelReservation(id));
    }

    @Test
    @DisplayName("List reservations by room successfully")
    void shouldFindReservationsByRoom() {
        when(roomRepository.existsById(room.getId())).thenReturn(true);
        when(repository.findReservationsByRoom(room.getId())).thenReturn(List.of(reservation));
        when(mapper.toDto(reservation)).thenReturn(ReservationResponseDTO.builder().build());

        List<ReservationResponseDTO> result = service.findReservationsByRoom(room.getId());

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Throw when room not found on findReservationsByRoom")
    void shouldThrowRoomNotFound() {
        when(roomRepository.existsById(room.getId())).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> service.findReservationsByRoom(room.getId()));
    }

    @Test
    @DisplayName("Group reservations by payment method")
    void shouldGroupReservations() {
        ReservationResponseDTO dto = ReservationResponseDTO.builder()
                .status(ReservationStatus.CONFIRMED)
                .build();

        when(repository.findAll()).thenReturn(List.of(reservation));
        when(mapper.toDto(reservation)).thenReturn(dto);

        Map<ReservationStatus, List<ReservationResponseDTO>> result =
                service.getReservationsGroupedByPaymentMethod();

        assertTrue(result.containsKey(ReservationStatus.CONFIRMED));
        assertEquals(1, result.get(ReservationStatus.CONFIRMED).size());
    }
}
