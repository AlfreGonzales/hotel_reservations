package project.hotel_reservations.service;

import project.hotel_reservations.dto.HotelAdminCreateDTO;
import project.hotel_reservations.dto.HotelAdminResponseDTO;
import project.hotel_reservations.dto.HotelAdminUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface HotelAdminService {

    HotelAdminResponseDTO create(HotelAdminCreateDTO req);

    List<HotelAdminResponseDTO> findAll();

    HotelAdminResponseDTO findById(UUID id);

    HotelAdminResponseDTO update(UUID id, HotelAdminUpdateDTO req);

    void delete(UUID id);
}
