package project.hotel_reservations.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import project.hotel_reservations.dto.payment.PaymentCreateDTO;
import project.hotel_reservations.factory.PaymentProcessor;
import project.hotel_reservations.factory.PaymentProcessorFactory;
import project.hotel_reservations.model.Payment;
import project.hotel_reservations.model.PaymentMethod;
import project.hotel_reservations.model.PaymentPlatform;
import project.hotel_reservations.model.Reservation;
import project.hotel_reservations.repository.PaymentPlatformRepository;
import project.hotel_reservations.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentPlatformRepository platformRepository;

    @InjectMocks
    private PaymentServiceImpl service;

    @Mock
    private PaymentProcessor processor;

    private Reservation reservation;
    private PaymentPlatform platform;

    private MockedStatic<PaymentProcessorFactory> factoryMock;

    @BeforeEach
    void setup() {
        reservation = Reservation.builder()
                .id(UUID.randomUUID())
                .build();

        platform = PaymentPlatform.builder()
                .id(UUID.randomUUID())
                .name("PayPal")
                .code("PP")
                .active(true)
                .build();
    }

    @AfterEach
    void closeStaticMocks() {
        if (factoryMock != null) factoryMock.close();
    }

    @Test
    @DisplayName("Process CASH payment successfully (no platform)")
    void shouldProcessCashPayment() {
        PaymentCreateDTO req = new PaymentCreateDTO(
                new BigDecimal("100"),
                PaymentMethod.CASH,
                reservation,
                null
        );

        Payment saved = Payment.builder()
                .id(UUID.randomUUID())
                .totalAmount(req.totalAmount())
                .paymentMethod(PaymentMethod.CASH)
                .reservation(reservation)
                .build();

        when(repository.save(any(Payment.class))).thenReturn(saved);

        Payment result = service.processPayment(req);

        assertEquals(PaymentMethod.CASH, result.getPaymentMethod());
        verify(repository).save(any(Payment.class));
        verifyNoInteractions(platformRepository);
    }

    @Test
    @DisplayName("CASH payment with platform → throw IllegalArgumentException")
    void shouldThrowCashWithPlatform() {
        PaymentCreateDTO req = new PaymentCreateDTO(
                new BigDecimal("100"),
                PaymentMethod.CASH,
                reservation,
                UUID.randomUUID()
        );

        assertThrows(IllegalArgumentException.class, () -> service.processPayment(req));
        verifyNoInteractions(repository);
        verifyNoInteractions(platformRepository);
    }

    @Test
    @DisplayName("TRANSFER without platform → throw IllegalArgumentException")
    void shouldThrowTransferNoPlatform() {
        PaymentCreateDTO req = new PaymentCreateDTO(
                new BigDecimal("100"),
                PaymentMethod.TRANSFER,
                reservation,
                null
        );

        assertThrows(IllegalArgumentException.class, () -> service.processPayment(req));
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("TRANSFER with non-existing platform → throw EntityNotFoundException")
    void shouldThrowTransferPlatformNotFound() {
        UUID platformId = UUID.randomUUID();

        PaymentCreateDTO req = new PaymentCreateDTO(
                new BigDecimal("100"),
                PaymentMethod.TRANSFER,
                reservation,
                platformId
        );

        when(platformRepository.findById(platformId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.processPayment(req));
        verifyNoInteractions(repository);
    }

    @Test
    @DisplayName("Transfer payment processed successfully")
    void shouldProcessTransferPayment() {

        UUID platformId = platform.getId();

        PaymentCreateDTO req = new PaymentCreateDTO(
                new BigDecimal("150"),
                PaymentMethod.TRANSFER,
                reservation,
                platformId
        );

        when(platformRepository.findById(platformId))
                .thenReturn(Optional.of(platform));

        factoryMock = Mockito.mockStatic(PaymentProcessorFactory.class);
        factoryMock.when(() -> PaymentProcessorFactory.getProcessor(platform))
                .thenReturn(processor);

        Payment saved = Payment.builder()
                .id(UUID.randomUUID())
                .totalAmount(req.totalAmount())
                .reservation(reservation)
                .paymentMethod(PaymentMethod.TRANSFER)
                .paymentPlatform(platform)
                .build();

        when(repository.save(any(Payment.class))).thenReturn(saved);

        Payment result = service.processPayment(req);

        assertEquals(PaymentMethod.TRANSFER, result.getPaymentMethod());
        assertEquals(platform, result.getPaymentPlatform());

        verify(processor).process(any(Payment.class));
        verify(repository).save(any(Payment.class));

        factoryMock.verify(() -> PaymentProcessorFactory.getProcessor(platform));
    }

}
