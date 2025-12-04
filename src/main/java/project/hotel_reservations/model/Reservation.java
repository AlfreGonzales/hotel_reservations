package project.hotel_reservations.model;

import jakarta.persistence.*;
import lombok.*;
import project.hotel_reservations.reservation.state.ReservationState;
import project.hotel_reservations.reservation.state.ReservationStateFactory;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "people_count", nullable = false)
    private Integer peopleCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PENDING;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @OneToOne(mappedBy = "reservation", fetch = FetchType.EAGER)
    private Payment payment;

    public ReservationState getState() {
        return ReservationStateFactory.fromStatus(this.status);
    }
}
