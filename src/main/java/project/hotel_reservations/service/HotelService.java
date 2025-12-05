package project.hotel_reservations.service;

import project.hotel_reservations.dto.hotel.HotelCreateDTO;
import project.hotel_reservations.dto.hotel.HotelResponseDTO;
import project.hotel_reservations.dto.hotel.HotelUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface HotelService {

    HotelResponseDTO create(HotelCreateDTO req);

    List<HotelResponseDTO> findAll();

    HotelResponseDTO findById(UUID id);

    HotelResponseDTO update(UUID id, HotelUpdateDTO req);

    void delete(UUID id);
}
