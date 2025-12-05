package project.hotel_reservations.factory;

import lombok.extern.slf4j.Slf4j;
import project.hotel_reservations.model.Payment;

@Slf4j
public class PayPalProcessor implements PaymentProcessor {

    @Override
    public void process(Payment payment) {
        log.info("PayPal processing started for payment");
        log.info("PayPal processing completed for payment");
    }
}
