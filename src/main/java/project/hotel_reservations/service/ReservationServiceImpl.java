package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hotel_reservations.dto.reservation.PayReservationDTO;
import project.hotel_reservations.dto.payment.PaymentCreateDTO;
import project.hotel_reservations.dto.reservation.ReservationCreateDTO;
import project.hotel_reservations.dto.reservation.ReservationResponseDTO;
import project.hotel_reservations.exception.ReservationNotFoundException;
import project.hotel_reservations.mapper.ReservationMapper;
import project.hotel_reservations.model.*;
import project.hotel_reservations.repository.GuestRepository;
import project.hotel_reservations.repository.ReservationRepository;
import project.hotel_reservations.repository.RoomRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationMapper mapper;
    private final PaymentService paymentService;

    /**
     * Creates a new reservation
     *
     * @param req DTO with creation data
     * @return DTO of the created reservation
     */
    @Override
    @Transactional
    public ReservationResponseDTO create(ReservationCreateDTO req) {
        Reservation entity = Reservation.builder()
                .checkInDate(req.checkInDate())
                .checkOutDate(req.checkOutDate())
                .peopleCount(req.peopleCount())
                .build();

        Room room = roomRepository.findById(req.roomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        Guest guest = guestRepository.findById(req.guestId())
                .orElseThrow(() -> new EntityNotFoundException("Guest not found"));

        entity.setRoom(room);
        entity.setGuest(guest);

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Return all reservations
     *
     * @return List of reservation DTOs
     */
    @Override
    public List<ReservationResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Return a reservation by ID
     *
     * @param id reservation ID
     * @return DTO of the found reservation
     * @throws EntityNotFoundException if reservation not found
     */
    @Override
    public ReservationResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    /**
     * Confirm a reservation
     *
     * @param id reservation ID
     * @param req DTO with payment data
     * @return DTO of the confirmed reservation
     * @throws EntityNotFoundException if reservation not found
     */
    @Override
    @Transactional
    public ReservationResponseDTO confirmReservation(UUID id, PayReservationDTO req) {
        Reservation entity = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        entity.getState().confirm(entity);

        PaymentCreateDTO paymentCreateDTO = PaymentCreateDTO.builder()
                .totalAmount(req.totalAmount())
                .paymentMethod(req.paymentMethod())
                .reservation(entity)
                .paymentPlatformId(req.paymentPlatformId())
                .build();

        Payment payment = paymentService.processPayment(paymentCreateDTO);

        entity.setPayment(payment);

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Cancel a reservation
     *
     * @param id reservation ID
     * @return DTO of the canceled reservation
     * @throws EntityNotFoundException if reservation not found
     */
    @Override
    @Transactional
    public ReservationResponseDTO cancelReservation(UUID id) {
        Reservation entity = repository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        entity.getState().cancel(entity);

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Return all reservations by room
     *
     * @return List of reservation DTOs
     */
    @Override
    public List<ReservationResponseDTO> findReservationsByRoom(UUID roomId) {
        if (!roomRepository.existsById(roomId)) {
            throw new EntityNotFoundException("Room not found");
        }

        return repository.findReservationsByRoom(roomId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Return all reservations grouped by payment method
     *
     * @return Map of reservation DTOs
     */
    @Override
    public Map<ReservationStatus, List<ReservationResponseDTO>> getReservationsGroupedByPaymentMethod() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.groupingBy(ReservationResponseDTO::status));
    }
}
