package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hotel_reservations.dto.HotelAdminCreateDTO;
import project.hotel_reservations.dto.HotelAdminResponseDTO;
import project.hotel_reservations.dto.HotelAdminUpdateDTO;
import project.hotel_reservations.mapper.HotelAdminMapper;
import project.hotel_reservations.model.Hotel;
import project.hotel_reservations.model.HotelAdmin;
import project.hotel_reservations.repository.HotelAdminRepository;
import project.hotel_reservations.repository.HotelRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HotelAdminServiceImpl implements HotelAdminService {

    private final HotelAdminRepository repository;
    private final HotelRepository hotelRepository;
    private final HotelAdminMapper mapper;

    /**
     * Creates a new hotel admin
     *
     * @param req DTO with creation data
     * @return DTO of the created hotel admin
     */
    @Override
    @Transactional
    public HotelAdminResponseDTO create(HotelAdminCreateDTO req) {
        HotelAdmin entity = HotelAdmin.builder()
                .identification(req.identification())
                .name(req.name())
                .email(req.email())
                .position(req.position())
                .shift(req.shift())
                .build();

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Return all hotel admins
     *
     * @return List of hotel admin DTOs
     */
    @Override
    public List<HotelAdminResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Return a hotel admin by ID
     *
     * @param id hotel admin ID
     * @return DTO of the found hotel admin
     * @throws EntityNotFoundException if hotel admin not found
     */
    @Override
    public HotelAdminResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    /**
     * Updates an existing hotel admin
     *
     * @param id hotel admin ID
     * @param req DTO with updated data
     * @return DTO of the updated hotel admin
     * @throws EntityNotFoundException if hotel admin not found
     */
    @Override
    @Transactional
    public HotelAdminResponseDTO update(UUID id, HotelAdminUpdateDTO req) {
        HotelAdmin entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        mapper.toEntity(req, entity);

        if (req.hotelId() != null) {
            Hotel hotel = hotelRepository.findById(req.hotelId())
                    .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

            entity.setHotel(hotel);
        }

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Deletes a hotel admin by ID
     *
     * @param id hotel admin ID
     */
    @Override
    @Transactional
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
