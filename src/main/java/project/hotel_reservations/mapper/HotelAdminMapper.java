package project.hotel_reservations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import project.hotel_reservations.dto.HotelAdminResponseDTO;
import project.hotel_reservations.dto.HotelAdminUpdateDTO;
import project.hotel_reservations.model.HotelAdmin;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HotelAdminMapper {

    @Mapping(source = "hotel.id", target = "hotelId")
    HotelAdminResponseDTO toDto(HotelAdmin entity);

    void toEntity(HotelAdminUpdateDTO dto, @MappingTarget HotelAdmin entity);
}
