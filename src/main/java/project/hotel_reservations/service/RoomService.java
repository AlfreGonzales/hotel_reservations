package project.hotel_reservations.service;

import project.hotel_reservations.dto.RoomCreateDTO;
import project.hotel_reservations.dto.RoomResponseDTO;
import project.hotel_reservations.dto.RoomUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    RoomResponseDTO create(RoomCreateDTO req);

    List<RoomResponseDTO> findAll();

    RoomResponseDTO findById(UUID id);

    RoomResponseDTO update(UUID id, RoomUpdateDTO req);

    void softDelete(UUID id);

    List<RoomResponseDTO> findAllDeleted();
}
