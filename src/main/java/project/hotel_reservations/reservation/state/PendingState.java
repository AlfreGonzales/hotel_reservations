package project.hotel_reservations.reservation.state;

import project.hotel_reservations.model.Reservation;
import project.hotel_reservations.model.ReservationStatus;

public class PendingState implements ReservationState {
    @Override
    public void confirm(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CONFIRMED);
    }

    @Override
    public void cancel(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCELLED);
    }
}
