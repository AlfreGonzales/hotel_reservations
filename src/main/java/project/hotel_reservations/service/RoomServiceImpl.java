package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hotel_reservations.dto.room.RoomCreateDTO;
import project.hotel_reservations.dto.room.RoomResponseDTO;
import project.hotel_reservations.dto.room.RoomUpdateDTO;
import project.hotel_reservations.mapper.RoomMapper;
import project.hotel_reservations.model.Hotel;
import project.hotel_reservations.model.Room;
import project.hotel_reservations.repository.HotelRepository;
import project.hotel_reservations.repository.RoomRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repository;
    private final HotelRepository hotelRepository;
    private final RoomMapper mapper;

    /**
     * Creates a new room
     *
     * @param req DTO with creation data
     * @return DTO of the created room
     */
    @Override
    @Transactional
    public RoomResponseDTO create(RoomCreateDTO req) {
        Room entity = Room.builder()
                .code(req.code())
                .capacity(req.capacity())
                .type(req.type())
                .price(req.price())
                .description(req.description())
                .build();

        Hotel hotel = hotelRepository.findById(req.hotelId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        entity.setHotel(hotel);

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Return all rooms
     *
     * @return List of room DTOs
     */
    @Override
    public List<RoomResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Return a room by ID
     *
     * @param id room ID
     * @return DTO of the found room
     * @throws EntityNotFoundException if room not found
     */
    @Override
    public RoomResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
    }

    /**
     * Updates an existing room
     *
     * @param id room ID
     * @param req DTO with updated data
     * @return DTO of the updated room
     * @throws EntityNotFoundException if room not found
     */
    @Override
    public RoomResponseDTO update(UUID id, RoomUpdateDTO req) {
        Room entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        mapper.toEntity(req, entity);

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Deletes a room by ID
     *
     * @param id room ID
     */
    @Override
    public void softDelete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Room not found");
        }

        repository.deleteById(id);
    }

    /**
     * Return all deleted rooms
     *
     * @return List of room DTOs
     */
    @Override
    public List<RoomResponseDTO> findAllDeleted() {
        return repository.findAllDeleted()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
