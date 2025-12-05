package project.hotel_reservations.service;

import project.hotel_reservations.dto.reservation.PayReservationDTO;
import project.hotel_reservations.dto.reservation.ReservationCreateDTO;
import project.hotel_reservations.dto.reservation.ReservationResponseDTO;
import project.hotel_reservations.model.ReservationStatus;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ReservationService {

    ReservationResponseDTO create(ReservationCreateDTO req);

    List<ReservationResponseDTO> findAll();

    ReservationResponseDTO findById(UUID id);

    ReservationResponseDTO confirmReservation(UUID id, PayReservationDTO req);

    ReservationResponseDTO cancelReservation(UUID id);

    List<ReservationResponseDTO> findReservationsByRoom(UUID roomId);

    Map<ReservationStatus, List<ReservationResponseDTO>> getReservationsGroupedByPaymentMethod();
}
