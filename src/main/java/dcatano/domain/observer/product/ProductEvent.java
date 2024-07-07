package dcatano.domain.observer.product;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventManager;
import dcatano.domain.observer.EventPublisher;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import lombok.Getter;

@Getter
public class ProductEvent implements EventPublisher<Product> {
    private final EventManager<Product> eventManager;

    public ProductEvent() {
        eventManager = new EventManager<>();
    }

    @Override
    public void publish(EventType eventType, Event<Product> event) {
        eventManager.notify(eventType, event);
    }
}
