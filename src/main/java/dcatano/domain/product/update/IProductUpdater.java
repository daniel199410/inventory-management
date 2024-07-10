package dcatano.domain.product.update;

import dcatano.domain.observer.EventType;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductUpdater {
    CompletableFuture<List<String>> updateQuantityAndPrice(ProductUpdateDTO productUpdateDTO, EventType eventType);
}
