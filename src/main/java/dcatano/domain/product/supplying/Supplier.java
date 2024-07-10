package dcatano.domain.product.supplying;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventListener;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.update.ProductUpdateDTO;
import dcatano.domain.product.update.ProductUpdater;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Supplier implements EventListener<Product> {
    private final ProductUpdater productUpdater;

    @Override
    public void update(EventType eventType, Event<Product> product) {
        if(shouldSupply(eventType, product.getPayload())) {
            productUpdater.updateQuantityAndPrice(new ProductUpdateDTO(
                product.getPayload().getId(),
                product.getPayload().getQuantity() + product.getPayload().getSupply().recharge(),
                product.getPayload().getPrice()
            ), EventType.UPDATE);
        }
    }

    private boolean shouldSupply(EventType eventType, Product product) {
        return EventType.UPDATE.equals(eventType) &&
            product.getSupply() != null &&
            product.getSupply().threshold() > product.getQuantity();
    }
}
