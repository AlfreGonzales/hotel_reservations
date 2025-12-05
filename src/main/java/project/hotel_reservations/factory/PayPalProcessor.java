package project.hotel_reservations.factory;

import project.hotel_reservations.model.Payment;

public class PayPalProcessor implements PaymentProcessor {

    @Override
    public void process(Payment payment) {
        System.out.println("Processing payment with PayPal...");
    }
}
