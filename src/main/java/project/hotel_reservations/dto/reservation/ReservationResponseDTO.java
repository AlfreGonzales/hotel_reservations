package project.hotel_reservations.dto.reservation;

import lombok.Builder;
import project.hotel_reservations.dto.payment.PaymentResponseDTO;
import project.hotel_reservations.model.ReservationStatus;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record ReservationResponseDTO(
        UUID id,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer peopleCount,
        ReservationStatus status,
        PaymentResponseDTO payment,
        UUID roomId,
        UUID guestId
) {
}
