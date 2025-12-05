package project.hotel_reservations.factory;

import project.hotel_reservations.model.PaymentPlatform;

public class PaymentProcessorFactory {

    public static PaymentProcessor getProcessor(PaymentPlatform platform) {
        return switch (platform.getCode().toUpperCase()) {
            case "PAYPAL" -> new PayPalProcessor();
            case "STRIPE" -> new StripeProcessor();
            default -> throw new IllegalArgumentException("Unsupported payment platform: " + platform.getCode());
        };
    }
}
