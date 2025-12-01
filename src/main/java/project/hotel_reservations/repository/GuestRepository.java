package project.hotel_reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hotel_reservations.model.Guest;

import java.util.UUID;

public interface GuestRepository extends JpaRepository<Guest, UUID> {
}
