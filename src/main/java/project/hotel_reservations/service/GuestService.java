package project.hotel_reservations.service;

import project.hotel_reservations.dto.GuestCreateDTO;
import project.hotel_reservations.dto.GuestResponseDTO;
import project.hotel_reservations.dto.GuestUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface GuestService {

    GuestResponseDTO create(GuestCreateDTO req);

    List<GuestResponseDTO> findAll();

    GuestResponseDTO findById(UUID id);

    GuestResponseDTO update(UUID id, GuestUpdateDTO req);

    void delete(UUID id);
}
