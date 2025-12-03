package project.hotel_reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hotel_reservations.model.Reservation;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
}
