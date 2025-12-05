package project.hotel_reservations.service;

import project.hotel_reservations.dto.reservation.PayReservationDTO;
import project.hotel_reservations.dto.reservation.ReservationCreateDTO;
import project.hotel_reservations.dto.reservation.ReservationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    ReservationResponseDTO create(ReservationCreateDTO req);

    List<ReservationResponseDTO> findAll();

    ReservationResponseDTO findById(UUID id);

    ReservationResponseDTO confirmReservation(UUID id, PayReservationDTO req);

    ReservationResponseDTO cancelReservation(UUID id);
}
