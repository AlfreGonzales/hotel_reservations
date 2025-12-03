package project.hotel_reservations.service;

import project.hotel_reservations.dto.PaymentPlatformCreateDTO;
import project.hotel_reservations.dto.PaymentPlatformResponseDTO;
import project.hotel_reservations.dto.PaymentPlatformUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface PaymentPlatformService {

    PaymentPlatformResponseDTO create(PaymentPlatformCreateDTO req);

    List<PaymentPlatformResponseDTO> findAll();

    PaymentPlatformResponseDTO findById(UUID id);

    PaymentPlatformResponseDTO update(UUID id, PaymentPlatformUpdateDTO req);

    void delete(UUID id);
}
