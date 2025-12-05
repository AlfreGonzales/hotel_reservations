package project.hotel_reservations.service;

import project.hotel_reservations.dto.guest.GuestCreateDTO;
import project.hotel_reservations.dto.guest.GuestResponseDTO;
import project.hotel_reservations.dto.guest.GuestUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface GuestService {

    GuestResponseDTO create(GuestCreateDTO req);

    List<GuestResponseDTO> findAll();

    GuestResponseDTO findById(UUID id);

    GuestResponseDTO update(UUID id, GuestUpdateDTO req);

    void delete(UUID id);
}
