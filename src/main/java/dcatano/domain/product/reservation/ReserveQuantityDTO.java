package dcatano.domain.product.reservation;

import java.util.UUID;

public record ReserveQuantityDTO(UUID productId, Integer quantity) {
}
