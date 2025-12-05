package project.hotel_reservations.factory;

import project.hotel_reservations.model.Payment;

public class StripeProcessor implements PaymentProcessor {

    @Override
    public void process(Payment payment) {
        System.out.println("Processing payment with Stripe...");
    }
}
