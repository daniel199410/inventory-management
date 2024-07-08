package dcatano.domain.product.reservation;

import dcatano.domain.observer.EventType;
import dcatano.domain.product.ProductMock;
import dcatano.domain.product.ProductRepository;
import dcatano.domain.product.update.ProductUpdateDTO;
import dcatano.domain.product.update.ProductUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReserverTest {
    private static final UUID testUUID = UUID.fromString("ccbcace8-69f1-4345-bca6-afdd3682d050");
    private static Set<Reservation> reservations = new HashSet<>();
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductUpdater productUpdater = mock(ProductUpdater.class);
    private final ReserverRepository reserverRepository = new ReserverRepository() {
        @Override
        public void save(Reservation reservation) {
            reservations.add(reservation);
        }

        @Override
        public Optional<Reservation> findByProductId(UUID id) {
            if(testUUID.equals(id)) {
                return Optional.empty();
            }
            return Optional.of(new Reservation(id, 1000));
        }

        @Override
        public void delete(Reservation reservation) {

        }
    };
    private final Reserver reserver = new Reserver(productRepository, productUpdater, reserverRepository);

    @BeforeEach
    public void beforeEach() {
        reservations = new HashSet<>();
    }

    @Test
    void shouldBeInvalidOnInvalidParam() throws ExecutionException, InterruptedException {
        assertEquals(Messages.INVALID_QUANTITY.getMessage(), reserver.reserveQuantity(new ReserveQuantityDTO(UUID.randomUUID(), null)).get().getFirst());
    }

    @Test
    void shouldBeInvalidOnNegativeQuantity() throws ExecutionException, InterruptedException {
        assertEquals(Messages.QUANTITY_LOWER_THAN_ZERO.getMessage(), reserver.reserveQuantity(new ReserveQuantityDTO(UUID.randomUUID(), -1)).get().getFirst());
    }

    @Test
    void shouldNotReserveOnProductNotFound() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
        assertEquals(Messages.PRODUCT_NOT_FOUND.getMessage(), reserver.reserveQuantity(new ReserveQuantityDTO(UUID.randomUUID(), 10)).get().getFirst());
    }

    @Test
    void shouldNotReserveOnProductQuantityNotEnoughForReservation() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(ProductMock.create()));
        assertEquals(Messages.PRODUCT_QUANTITY_NOT_ENOUGH.getMessage(), reserver.reserveQuantity(new ReserveQuantityDTO(UUID.randomUUID(), 1001)).get().getFirst());
    }

    @Test
    void shouldProcessNewReservation() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(ProductMock.create(testUUID)));
        Mockito.when(productUpdater.updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class))).thenReturn(CompletableFuture.supplyAsync(Collections::emptyList));
        reserver.reserveQuantity(new ReserveQuantityDTO(UUID.randomUUID(), 100)).get();
        reservations.stream().findFirst().ifPresent(reservation -> assertEquals(100, reservation.quantity()));
    }

    @Test
    void shouldProcessExistentReservation() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(ProductMock.create()));
        Mockito.when(productUpdater.updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class))).thenReturn(CompletableFuture.supplyAsync(Collections::emptyList));
        reserver.reserveQuantity(new ReserveQuantityDTO(UUID.randomUUID(), 100)).get();
        reservations.stream().findFirst().ifPresent(reservation -> assertEquals(1100, reservation.quantity()));
    }

    @Test
    void shouldNotReleaseReservationIfReservationIsNotFound() throws ExecutionException, InterruptedException {
        assertEquals(Messages.RESERVATION_NOT_FOUND.getMessage(), reserver.releaseReservation(testUUID).get().getFirst());
    }

    @Test
    void shouldReleaseReservationAndUpdateProductQuantity() throws ExecutionException, InterruptedException {
        Mockito.when(productUpdater.updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class))).thenReturn(CompletableFuture.supplyAsync(Collections::emptyList));
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(ProductMock.create()));
        assertTrue(reserver.releaseReservation(UUID.randomUUID()).get().isEmpty());
        verify(productUpdater, times(1)).updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class));
    }

    @Test
    void shouldReleaseReservationAndNotUpdateNotExistentProduct() throws ExecutionException, InterruptedException {
        Mockito.when(productUpdater.updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class))).thenReturn(CompletableFuture.supplyAsync(Collections::emptyList));
        assertEquals(Messages.PRODUCT_NOT_FOUND.getMessage(), reserver.releaseReservation(UUID.randomUUID()).get().getFirst());
        verify(productUpdater, times(0)).updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class));
    }
}
