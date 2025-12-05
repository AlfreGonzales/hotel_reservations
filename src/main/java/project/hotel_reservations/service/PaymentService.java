package project.hotel_reservations.service;

import project.hotel_reservations.dto.payment.PaymentCreateDTO;
import project.hotel_reservations.model.Payment;

public interface PaymentService {
    Payment processPayment(PaymentCreateDTO req);
}
