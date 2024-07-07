package dcatano.domain.product;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    void save(Product product);

    Optional<Product> findById(UUID id);
}
