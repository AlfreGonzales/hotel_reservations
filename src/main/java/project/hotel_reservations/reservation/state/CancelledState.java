package project.hotel_reservations.reservation.state;

import project.hotel_reservations.model.Reservation;

public class CancelledState implements ReservationState {
    @Override
    public void confirm(Reservation reservation) {
        throw new IllegalStateException("Cancelled reservations cannot be confirmed");
    }

    @Override
    public void cancel(Reservation reservation) {
        throw new IllegalStateException("Reservation is already cancelled");
    }
}
