package project.hotel_reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.hotel_reservations.model.Hotel;

import java.math.BigDecimal;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    @Query(value = """
        SELECT SUM(p.total_amount)
        FROM payments p
        JOIN reservations r ON p.reservation_id = r.id
        JOIN rooms ro ON r.room_id = ro.id
        WHERE ro.hotel_id = :hotelId
    """, nativeQuery = true)
    BigDecimal getTotalEarningsByHotel(UUID hotelId);
}
