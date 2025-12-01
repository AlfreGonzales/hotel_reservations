package project.hotel_reservations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import project.hotel_reservations.dto.GuestResponseDTO;
import project.hotel_reservations.dto.GuestUpdateDTO;
import project.hotel_reservations.model.Guest;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GuestMapper {

    GuestResponseDTO toDto(Guest entity);

    void toEntity(GuestUpdateDTO dto, @MappingTarget Guest entity);
}
