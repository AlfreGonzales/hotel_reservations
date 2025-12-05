package project.hotel_reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.hotel_reservations.model.Room;

import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {

    @Query(value = "SELECT * FROM rooms WHERE deleted = true", nativeQuery = true)
    List<Room> findAllDeleted();

    List<Room> findByHotelId(UUID hotelId);

    @Query("""
        SELECT r FROM Room r
        WHERE r.hotel.id = :hotelId
        AND r.available = true
    """)
    List<Room> findAvailableRooms(UUID hotelId);
}
