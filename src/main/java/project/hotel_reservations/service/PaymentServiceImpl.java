package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.hotel_reservations.dto.payment.PaymentCreateDTO;
import project.hotel_reservations.model.Payment;
import project.hotel_reservations.model.PaymentMethod;
import project.hotel_reservations.model.PaymentPlatform;
import project.hotel_reservations.factory.PaymentProcessorFactory;
import project.hotel_reservations.factory.PaymentProcessor;
import project.hotel_reservations.repository.PaymentPlatformRepository;
import project.hotel_reservations.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final PaymentPlatformRepository paymentPlatformRepository;

    /**
     * Processes and creates a payment
     *
     * @param req DTO with creation data
     * @return DTO of the created payment
     * @throws IllegalArgumentException   if payment platform is invalid
     * @throws EntityNotFoundException    if payment platform not found
     */
    @Override
    @Transactional
    public Payment processPayment(PaymentCreateDTO req) {
        Payment entity = Payment.builder()
                .totalAmount(req.totalAmount())
                .paymentMethod(req.paymentMethod())
                .reservation(req.reservation())
                .build();

        if (entity.getPaymentMethod() == PaymentMethod.CASH) {

            if (req.paymentPlatformId() != null) {
                throw new IllegalArgumentException("CASH payments must not specify a payment platform");
            }

            return repository.save(entity);
        }

        if (entity.getPaymentMethod() == PaymentMethod.TRANSFER) {

            if (req.paymentPlatformId() == null) {
                throw new IllegalArgumentException("TRANSFER payments require a payment platform");
            }

            PaymentPlatform paymentPlatform = paymentPlatformRepository.findById(req.paymentPlatformId())
                    .orElseThrow(() -> new EntityNotFoundException("Payment platform not found"));

            entity.setPaymentPlatform(paymentPlatform);

            PaymentProcessor processor = PaymentProcessorFactory.getProcessor(paymentPlatform);

            processor.process(entity);

            return repository.save(entity);
        }

        return null;
    }
}
