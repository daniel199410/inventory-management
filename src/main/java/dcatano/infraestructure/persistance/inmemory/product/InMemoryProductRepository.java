package dcatano.infraestructure.persistance.inmemory.product;

import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;

import java.util.Optional;
import java.util.UUID;

public class InMemoryProductRepository implements ProductRepository {
    @Override
    public void save(Product product) {
        DBProduct dbProduct = DBProduct.fromDomain(product);
        InMemoryPersistence.getProducts().add(dbProduct);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return InMemoryPersistence.getProducts().stream().filter(p -> p.id().equals(id)).findFirst().map(DBProduct::toDomain);
    }
}
