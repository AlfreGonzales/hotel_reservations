package project.hotel_reservations.payment.factory;

import project.hotel_reservations.model.PaymentPlatform;
import project.hotel_reservations.payment.processor.PayPalProcessor;
import project.hotel_reservations.payment.processor.PaymentProcessor;
import project.hotel_reservations.payment.processor.StripeProcessor;

public class PaymentProcessorFactory {

    public static PaymentProcessor getProcessor(PaymentPlatform platform) {
        return switch (platform.getCode().toUpperCase()) {
            case "PAYPAL" -> new PayPalProcessor();
            case "STRIPE" -> new StripeProcessor();
            default -> throw new IllegalArgumentException("Unsupported payment platform: " + platform.getCode());
        };
    }
}
