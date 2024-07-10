package dcatano.domain.product.reservation;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IReserver {
    CompletableFuture<List<String>> reserveQuantity(ReserveQuantityDTO reserveQuantityDTO);

    CompletableFuture<List<String>> releaseReservation(UUID productId);
}
