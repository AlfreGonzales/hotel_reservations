package project.hotel_reservations.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("HOTEL_ADMIN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class HotelAdmin extends User {

    private String position;

    @Enumerated(EnumType.STRING)
    private HotelAdminShift shift;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
}
