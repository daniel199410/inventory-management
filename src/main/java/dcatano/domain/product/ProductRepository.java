package dcatano.domain.product;

import dcatano.domain.product.search.ProductSearchFilters;
import dcatano.infraestructure.persistance.inmemory.product.OptimisticLockException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    void save(Product product) throws OptimisticLockException;

    Optional<Product> findById(UUID id);

    List<Product> findByFilter(ProductSearchFilters productSearchFilters);

    void delete(Product payload);
}
