package dcatano.domain.product.update;

import java.util.UUID;

public record ProductUpdateDTO(UUID id, Integer quantity, Double price, boolean replace) {
}
