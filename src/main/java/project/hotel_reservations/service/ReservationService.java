package project.hotel_reservations.service;

import project.hotel_reservations.dto.PayReservationDTO;
import project.hotel_reservations.dto.ReservationCreateDTO;
import project.hotel_reservations.dto.ReservationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    ReservationResponseDTO create(ReservationCreateDTO req);

    List<ReservationResponseDTO> findAll();

    ReservationResponseDTO findById(UUID id);

    ReservationResponseDTO confirmReservation(UUID id, PayReservationDTO req);
}
