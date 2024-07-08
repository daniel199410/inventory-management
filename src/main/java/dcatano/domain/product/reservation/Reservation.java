package dcatano.domain.product.reservation;

import java.util.UUID;

public record Reservation(UUID productId, Integer quantity) {
}
