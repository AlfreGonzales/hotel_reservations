package project.hotel_reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hotel_reservations.model.HotelAdmin;

import java.util.UUID;

public interface HotelAdminRepository extends JpaRepository<HotelAdmin, UUID> {
}
