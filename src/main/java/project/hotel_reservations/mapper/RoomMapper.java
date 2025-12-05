package project.hotel_reservations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import project.hotel_reservations.dto.room.RoomResponseDTO;
import project.hotel_reservations.dto.room.RoomUpdateDTO;
import project.hotel_reservations.model.Room;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoomMapper {

    @Mapping(source = "hotel.id", target = "hotelId")
    RoomResponseDTO toDto(Room entity);

    void toEntity(RoomUpdateDTO dto, @MappingTarget Room entity);
}
