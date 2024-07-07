package dcatano.domain.product;

import dcatano.domain.product.search.ProductSearchFilters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    void save(Product product);

    Optional<Product> findById(UUID id);

    List<Product> findByFilter(ProductSearchFilters productSearchFilters);
}
