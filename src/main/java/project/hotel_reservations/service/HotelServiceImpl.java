package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hotel_reservations.dto.HotelCreateDTO;
import project.hotel_reservations.dto.HotelResponseDTO;
import project.hotel_reservations.dto.HotelUpdateDTO;
import project.hotel_reservations.mapper.HotelMapper;
import project.hotel_reservations.model.Hotel;
import project.hotel_reservations.repository.HotelRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;
    private final HotelMapper mapper;

    /**
     * Creates a new hotel
     *
     * @param req DTO with creation data
     * @return DTO of the created hotel
     */
    @Override
    @Transactional
    public HotelResponseDTO create(HotelCreateDTO req) {
        Hotel entity = Hotel.builder()
                .name(req.name())
                .address(req.address())
                .phone(req.phone())
                .email(req.email())
                .build();
        return mapper.toDto(repository.save(entity));
    }

    /**
     * Return all hotels
     *
     * @return List of hotel DTOs
     */
    @Override
    public List<HotelResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Return a hotel by ID
     *
     * @param id hotel ID
     * @return DTO of the found hotel
     * @throws EntityNotFoundException if hotel not found
     */
    @Override
    public HotelResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));
    }

    /**
     * Updates an existing hotel
     *
     * @param id hotel ID
     * @param req DTO with updated data
     * @return DTO of the updated hotel
     * @throws EntityNotFoundException if hotel not found
     */
    @Override
    @Transactional
    public HotelResponseDTO update(UUID id, HotelUpdateDTO req) {
        Hotel entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        mapper.toEntity(req, entity);

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Deletes a hotel by ID
     *
     * @param id hotel ID
     */
    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
