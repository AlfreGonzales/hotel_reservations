package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hotel_reservations.dto.GuestCreateDTO;
import project.hotel_reservations.dto.GuestResponseDTO;
import project.hotel_reservations.dto.GuestUpdateDTO;
import project.hotel_reservations.mapper.GuestMapper;
import project.hotel_reservations.model.Guest;
import project.hotel_reservations.repository.GuestRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository repository;
    private final GuestMapper mapper;

    /**
     * Creates a new guest
     *
     * @param req DTO with creation data
     * @return DTO of the created guest
     */
    @Override
    @Transactional
    public GuestResponseDTO create(GuestCreateDTO req) {
        Guest entity = Guest.builder()
                .identification(req.identification())
                .name(req.name())
                .email(req.email())
                .birthDate(req.birthDate())
                .nationality(req.nationality())
                .build();

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Return all guests
     *
     * @return List of guest DTOs
     */
    @Override
    public List<GuestResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Return a guest by ID
     *
     * @param id guest ID
     * @return DTO of the found guest
     * @throws EntityNotFoundException if guest not found
     */
    @Override
    public GuestResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    /**
     * Updates an existing guest
     *
     * @param id guest ID
     * @param req DTO with updated data
     * @return DTO of the updated guest
     * @throws EntityNotFoundException if guest not found
     */
    @Override
    @Transactional
    public GuestResponseDTO update(UUID id, GuestUpdateDTO req) {
        Guest entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        mapper.toEntity(req, entity);

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Deletes a guest by ID
     *
     * @param id guest ID
     */
    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }

        repository.deleteById(id);
    }
}
