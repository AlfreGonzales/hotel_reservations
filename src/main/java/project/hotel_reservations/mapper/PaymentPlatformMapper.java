package project.hotel_reservations.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformResponseDTO;
import project.hotel_reservations.dto.payment_platform.PaymentPlatformUpdateDTO;
import project.hotel_reservations.model.PaymentPlatform;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentPlatformMapper {

    PaymentPlatformResponseDTO toDto(PaymentPlatform entity);

    void toEntity(PaymentPlatformUpdateDTO dto, @MappingTarget PaymentPlatform entity);
}
