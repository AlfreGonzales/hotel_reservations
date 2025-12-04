package project.hotel_reservations.payment.processor;

import project.hotel_reservations.model.Payment;

public interface PaymentProcessor {
    void process(Payment payment);
}
