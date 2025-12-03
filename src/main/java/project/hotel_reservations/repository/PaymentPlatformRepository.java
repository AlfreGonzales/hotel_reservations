package project.hotel_reservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hotel_reservations.model.PaymentPlatform;

import java.util.UUID;

public interface PaymentPlatformRepository extends JpaRepository<PaymentPlatform, UUID> {
}
