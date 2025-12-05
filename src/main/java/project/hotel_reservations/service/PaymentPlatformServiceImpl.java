package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformCreateDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformResponseDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformUpdateDTO;
import project.hotel_reservations.mapper.PaymentPlatformMapper;
import project.hotel_reservations.model.PaymentPlatform;
import project.hotel_reservations.repository.PaymentPlatformRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentPlatformServiceImpl implements PaymentPlatformService {

    private final PaymentPlatformRepository repository;
    private final PaymentPlatformMapper mapper;

    /**
     * Creates a new payment platform
     *
     * @param req DTO with creation data
     * @return DTO of the created payment platform
     */
    @Override
    @Transactional
    public PaymentPlatformResponseDTO create(PaymentPlatformCreateDTO req) {
        PaymentPlatform entity = PaymentPlatform.builder()
                .name(req.name())
                .code(req.code())
                .build();

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Return all payment platforms
     *
     * @return List of payment platform DTOs
     */
    @Override
    public List<PaymentPlatformResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Return a payment platform by ID
     *
     * @param id payment platform ID
     * @return DTO of the found payment platform
     * @throws EntityNotFoundException if payment platform not found
     */
    @Override
    public PaymentPlatformResponseDTO findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Payment platform not found"));
    }

    /**
     * Updates an existing payment platform
     *
     * @param id payment platform ID
     * @param req DTO with updated data
     * @return DTO of the updated payment platform
     * @throws EntityNotFoundException if payment platform not found
     */
    @Override
    @Transactional
    public PaymentPlatformResponseDTO update(UUID id, PaymentPlatformUpdateDTO req) {
        PaymentPlatform entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment platform not found"));

        mapper.toEntity(req, entity);

        return mapper.toDto(repository.save(entity));
    }

    /**
     * Deletes a payment platform by ID
     *
     * @param id payment platform ID
     */
    @Override
    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Payment platform not found");
        }

        repository.deleteById(id);
    }
}
