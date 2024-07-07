package dcatano.domain.product.elimination;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventListener;
import dcatano.domain.observer.EventPublisher;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductQuantityListener implements EventListener<Product> {
    private final ProductRepository productRepository;
    private final EventPublisher<Product> eventPublisher;

    @Override
    public void update(EventType eventType, Event<Product> event) {
        if(EventType.UPDATE.equals(eventType) && event.getPayload().getQuantity().equals(0)) {
            productRepository.delete(event.getPayload());
            eventPublisher.publish(EventType.ELIMINATION, event);
        }
    }
}
