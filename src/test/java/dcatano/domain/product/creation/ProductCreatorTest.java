package dcatano.domain.product.creation;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventListener;
import dcatano.domain.observer.EventType;
import dcatano.domain.observer.product.ProductEvent;
import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

public class ProductCreatorTest {

    @Test
    void shouldBeInvalidProduct() {
        final ProductCreator productCreator = new ProductCreator(null, null);
        assertEquals(5, productCreator.create(new ProductCreatorDTO(null, null, -3, -4, -5.0)).size());
    }

    @Test
    void shouldSaveProductAndEmitCreationEvent() {
        EventListener<Product> eventListener = new EventListener<>() {
            @Override
            public void update(EventType eventType, Event<Product> product) {

            }
        };
        EventListener<Product> eventListenerSpy = spy(eventListener);
        ProductRepository productRepository = product -> {

        };
        ProductEvent productEvent = new ProductEvent();
        productEvent.getEventManager().subscribe(EventType.CREATION, eventListenerSpy);
        ProductCreator productCreator = new ProductCreator(productRepository, productEvent);
        productCreator.create(new ProductCreatorDTO("Televisor", "Tecnología", 100, 10, 2_500_000.0));
        Mockito.verify(eventListenerSpy, Mockito.times(1)).update(Mockito.any(EventType.class), Mockito.any());
    }
}
