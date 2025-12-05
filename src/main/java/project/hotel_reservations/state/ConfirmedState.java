package project.hotel_reservations.state;

import project.hotel_reservations.model.Reservation;
import project.hotel_reservations.model.ReservationStatus;

public class ConfirmedState implements ReservationState {
    @Override
    public void confirm(Reservation reservation) {
        throw new IllegalStateException("Reservation is already confirmed");
    }

    @Override
    public void cancel(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCELLED);
    }
}
