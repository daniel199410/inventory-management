package dcatano.domain.product.supplying;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventListener;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.update.IProductUpdater;
import dcatano.domain.product.update.ProductUpdateDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Supplier implements EventListener<Product> {
    private final IProductUpdater productUpdater;

    @Override
    public void update(EventType eventType, Event<Product> product) {
        if(shouldSupply(eventType, product.getPayload())) {
            productUpdater.updateQuantityAndPrice(new ProductUpdateDTO(
                product.getPayload().getId(),
                product.getPayload().getSupply().recharge(),
                product.getPayload().getPrice()
            ), EventType.UPDATE);
        }
    }

    private boolean shouldSupply(EventType eventType, Product product) {
        return EventType.UPDATE.equals(eventType) &&
            product.getSupply() != null &&
            product.getSupply().threshold() >= product.getQuantity();
    }
}
