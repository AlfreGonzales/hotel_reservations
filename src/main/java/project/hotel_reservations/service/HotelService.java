package project.hotel_reservations.service;

import project.hotel_reservations.dto.HotelCreateDTO;
import project.hotel_reservations.dto.HotelResponseDTO;
import project.hotel_reservations.dto.HotelUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface HotelService {

    HotelResponseDTO create(HotelCreateDTO req);

    List<HotelResponseDTO> findAll();

    HotelResponseDTO findById(UUID id);

    HotelResponseDTO update(UUID id, HotelUpdateDTO req);

    void delete(UUID id);
}
