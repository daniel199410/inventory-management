package dcatano.domain.product.update;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventPublisher;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.ProductMock;
import dcatano.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductUpdaterTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final EventPublisher<Product> eventPublisher = mock(EventPublisher.class);
    private final ProductUpdater productUpdater = new ProductUpdater(productRepository, eventPublisher);

    @Test
    void shouldNotUpdateProductIfBothQuantityAndPriceAreNull() throws ExecutionException, InterruptedException {
        assertEquals(Messages.INVALID_PRICE_AND_QUANTITY.getMessage(), productUpdater.updateQuantityAndPrice(new ProductUpdateDTO(UUID.randomUUID(), null, null, true), EventType.UPDATE).get().getFirst());
    }

    @Test
    void shouldNotUpdateOnProductNotFound() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
        assertEquals(Messages.PRODUCT_NOT_FOUND.getMessage(), productUpdater.updateQuantityAndPrice(new ProductUpdateDTO(UUID.randomUUID(), 100, null, true), EventType.UPDATE).get().getFirst());
    }

    @Test
    void shouldUpdateProductAndEmitEvent() throws ExecutionException, InterruptedException {
        Mockito.when(productRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(ProductMock.create()));
        productUpdater.updateQuantityAndPrice(new ProductUpdateDTO(UUID.randomUUID(), 100, null, true), EventType.UPDATE).get();
        verify(eventPublisher, times(1)).publish(eq(EventType.UPDATE), Mockito.any(Event.class));
    }
}
