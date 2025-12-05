package project.hotel_reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hotel_reservations.model.Payment;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
