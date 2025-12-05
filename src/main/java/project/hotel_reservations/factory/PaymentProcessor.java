package project.hotel_reservations.factory;

import project.hotel_reservations.model.Payment;

public interface PaymentProcessor {
    void process(Payment payment);
}
