package dcatano.domain.product.supplying;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventManager;
import dcatano.domain.observer.EventPublisher;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.Supply;
import dcatano.domain.product.update.ProductUpdateDTO;
import dcatano.domain.product.update.ProductUpdater;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SupplierTest {
    private final ProductUpdater productUpdater = mock(ProductUpdater.class);
    private final Supplier supplier = new Supplier(productUpdater);

    @Test
    void shouldNotSupplyIfEventIsNotUpdate() throws InterruptedException {
        TestEvent eventPublisher = new TestEvent();
        eventPublisher.getEventManager().subscribe(EventType.RESERVATION, supplier);
        eventPublisher.publish(EventType.RESERVATION, new Event<>(new Product("", "", 1, null, 1.0)));
        Thread.sleep(500);
        verify(productUpdater, times(0)).updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class));
    }

    @Test
    void shouldNotUpdateProductQuantityIfSupplyIsNull() throws InterruptedException {
        TestEvent eventPublisher = new TestEvent();
        eventPublisher.getEventManager().subscribe(EventType.UPDATE, supplier);
        eventPublisher.publish(EventType.UPDATE, new Event<>(new Product("", "", 1, null, 1.0)));
        Thread.sleep(500);
        verify(productUpdater, times(0)).updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class));
    }

    @Test
    void shouldNotUpdateProductQuantityIfQuantityIsGreaterThanThreshold() throws InterruptedException {
        TestEvent eventPublisher = new TestEvent();
        eventPublisher.getEventManager().subscribe(EventType.UPDATE, supplier);
        eventPublisher.publish(EventType.UPDATE, new Event<>(new Product("", "", 11, new Supply(10, 10), 1.0)));
        Thread.sleep(500);
        verify(productUpdater, times(0)).updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class));
    }

    @Test
    void shouldUpdateProductQuantityIfQuantityIsLowerThanThreshold() throws InterruptedException {
        TestEvent eventPublisher = new TestEvent();
        eventPublisher.getEventManager().subscribe(EventType.UPDATE, supplier);
        eventPublisher.publish(EventType.UPDATE, new Event<>(new Product("", "", 9, new Supply(10, 10), 1.0)));
        Thread.sleep(500);
        verify(productUpdater, times(1)).updateQuantityAndPrice(Mockito.any(ProductUpdateDTO.class), Mockito.any(EventType.class));
    }
}

@Getter
class TestEvent implements EventPublisher<Product> {
    private final EventManager<Product> eventManager;

    public TestEvent() {
        eventManager = new EventManager<>();
    }

    @Override
    public void publish(EventType eventType, Event<Product> event) {
        eventManager.notify(eventType, event);
    }
}