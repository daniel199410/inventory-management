package dcatano.domain.product.elimination;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventManager;
import dcatano.domain.observer.EventPublisher;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductQuantityListenerTest {
    EventPublisher<Product> eventListener = new EventPublisher<>() {
        @Override
        public void publish(EventType eventType, Event<Product> event) {

        }
    };
    EventPublisher<Product> eventListenerSpy = spy(eventListener);
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductQuantityListener productQuantityListener = new ProductQuantityListener(productRepository, eventListenerSpy);

    @Test
    void shouldDeleteAndEmitEventOnEventReceivedWithProductQuantity0() throws InterruptedException {
        TestEvent testEvent = new TestEvent();
        testEvent.getEventManager().subscribe(EventType.UPDATE, productQuantityListener);
        testEvent.publish(EventType.UPDATE, new Event<>(new Product("", "", 0, 1, 1.0)));
        Thread.sleep(1000);
        verify(eventListenerSpy, times(1)).publish(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldNotDeleteAndEmitEventOnEventReceivedWithProductQuantityGreaterThanZero() {
        TestEvent testEvent = new TestEvent();
        testEvent.getEventManager().subscribe(EventType.UPDATE, productQuantityListener);
        testEvent.publish(EventType.UPDATE, new Event<>(new Product("", "", 10, 1, 1.0)));
        verify(eventListenerSpy, times(0)).publish(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldNotDeleteAndEmitEventOnNotUpdateEventReceived() {
        TestEvent testEvent = new TestEvent();
        testEvent.getEventManager().subscribe(EventType.ELIMINATION, productQuantityListener);
        testEvent.getEventManager().subscribe(EventType.CREATION, productQuantityListener);
        testEvent.publish(EventType.UPDATE, new Event<>(new Product("", "", 10, 1, 1.0)));
        verify(eventListenerSpy, times(0)).publish(Mockito.any(), Mockito.any());
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