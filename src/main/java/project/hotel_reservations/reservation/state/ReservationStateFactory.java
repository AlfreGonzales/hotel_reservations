package project.hotel_reservations.reservation.state;

import project.hotel_reservations.model.ReservationStatus;

public class ReservationStateFactory {

    public static ReservationState fromStatus(ReservationStatus status) {
        return switch (status) {
            case PENDING -> new PendingState();
            case CONFIRMED -> new ConfirmedState();
            case CANCELLED -> new CancelledState();
        };
    }
}
