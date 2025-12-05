package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("Starting payment process. reservationId={}, total={}",
                req.reservation().getId(), req.totalAmount());

        Payment entity = Payment.builder()
                .totalAmount(req.totalAmount())
                .paymentMethod(req.paymentMethod())
                .reservation(req.reservation())
                .build();

        log.debug("Payment entity created: {}", entity);

        if (entity.getPaymentMethod() == PaymentMethod.CASH) {

            if (req.paymentPlatformId() != null) {
                log.warn("CASH payment received with platformId={}, this is invalid",
                        req.paymentPlatformId());
                throw new IllegalArgumentException("CASH payments must not specify a payment platform");
            }

            log.info("Processing CASH payment");
            return repository.save(entity);
        }

        if (entity.getPaymentMethod() == PaymentMethod.TRANSFER) {

            if (req.paymentPlatformId() == null) {
                log.error("TRANSFER method but no paymentPlatformId was provided");
                throw new IllegalArgumentException("TRANSFER payments require a payment platform");
            }

            log.info("Fetching PaymentPlatform id={}", req.paymentPlatformId());
            PaymentPlatform paymentPlatform = paymentPlatformRepository.findById(req.paymentPlatformId())
                    .orElseThrow(() -> new EntityNotFoundException("Payment platform not found"));

            entity.setPaymentPlatform(paymentPlatform);
            log.info("PaymentPlatform assigned: {}", paymentPlatform.getName());

            PaymentProcessor processor = PaymentProcessorFactory.getProcessor(paymentPlatform);

            processor.process(entity);

            log.info("Payment processed successfully by {}", processor.getClass().getSimpleName());

            return repository.save(entity);
        }

        return null;
    }
}
