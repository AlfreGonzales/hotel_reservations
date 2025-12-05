package project.hotel_reservations.factory;

import lombok.extern.slf4j.Slf4j;
import project.hotel_reservations.model.Payment;

@Slf4j
public class StripeProcessor implements PaymentProcessor {

    @Override
    public void process(Payment payment) {
        log.info("Stripe processing started for payment");
        log.info("Stripe processing completed for payment");
    }
}
