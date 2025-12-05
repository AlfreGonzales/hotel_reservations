package project.hotel_reservations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import project.hotel_reservations.dto.hotel.HotelResponseDTO;
import project.hotel_reservations.dto.hotel.HotelUpdateDTO;
import project.hotel_reservations.model.Hotel;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HotelMapper {

    HotelResponseDTO toDto(Hotel entity);

    void toEntity(HotelUpdateDTO dto, @MappingTarget Hotel entity);
}
