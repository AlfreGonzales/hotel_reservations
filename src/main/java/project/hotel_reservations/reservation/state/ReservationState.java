package project.hotel_reservations.reservation.state;

import project.hotel_reservations.model.Reservation;

public interface ReservationState {
    void confirm(Reservation reservation);
    void cancel(Reservation reservation);
}
