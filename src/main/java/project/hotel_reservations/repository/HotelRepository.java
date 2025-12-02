package project.hotel_reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hotel_reservations.model.Hotel;

import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {
}
