package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hotel_reservations.dto.ReservationCreateDTO;
import project.hotel_reservations.dto.ReservationResponseDTO;
import project.hotel_reservations.mapper.ReservationMapper;
import project.hotel_reservations.model.Guest;
import project.hotel_reservations.model.Reservation;
import project.hotel_reservations.model.Room;
import project.hotel_reservations.repository.GuestRepository;
import project.hotel_reservations.repository.ReservationRepository;
import project.hotel_reservations.repository.RoomRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository repository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationMapper mapper;

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
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }
}
