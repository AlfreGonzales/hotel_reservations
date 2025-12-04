package project.hotel_reservations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import project.hotel_reservations.dto.ReservationResponseDTO;
import project.hotel_reservations.model.Reservation;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReservationMapper {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "guest.id", target = "guestId")
    @Mapping(source = "payment.paymentPlatform.id", target = "payment.paymentPlatformId")
    ReservationResponseDTO toDto(Reservation entity);
}
