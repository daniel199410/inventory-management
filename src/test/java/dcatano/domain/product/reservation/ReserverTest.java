package dcatano.domain.product.reservation;

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
import static org.mockito.Mockito.mock;

public class ReserverTest {
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
            if(UUID.fromString("ccbcace8-69f1-4345-bca6-afdd3682d050").equals(id)) {
                return Optional.empty();
            }
            return Optional.of(new Reservation(id, 1000));
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
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(ProductMock.create(UUID.fromString("ccbcace8-69f1-4345-bca6-afdd3682d050"))));
        Mockito.when(productUpdater.updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class))).thenReturn(CompletableFuture.supplyAsync(Collections::emptyList));
        reserver.reserveQuantity(new ReserveQuantityDTO(UUID.randomUUID(), 100)).get();
        reservations.stream().findFirst().ifPresent(reservation -> assertEquals(100, reservation.quantity()));
    }

    @Test
    void shouldProcessExistentReservation() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(ProductMock.create()));
        Mockito.when(productUpdater.updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class))).thenReturn(CompletableFuture.supplyAsync(Collections::emptyList));
        reserver.reserveQuantity(new ReserveQuantityDTO(UUID.randomUUID(), 100)).get();
        reservations.stream().findFirst().ifPresent(reservation -> assertEquals(1100, reservation.quantity()));
    }
}
