package project.hotel_reservations.dto.room;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record RoomResponseDTO(
    UUID id,
    String code,
    Integer capacity,
    String type,
    BigDecimal price,
    String description,
    Boolean available,
    UUID hotelId,
    Boolean deleted,
    LocalDateTime deletedAt
) {
}
