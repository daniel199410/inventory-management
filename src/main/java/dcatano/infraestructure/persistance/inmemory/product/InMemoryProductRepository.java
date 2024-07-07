package dcatano.infraestructure.persistance.inmemory.product;

import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;

public class InMemoryProductRepository implements ProductRepository {
    @Override
    public void save(Product product) {
        DBProduct dbProduct = DBProduct.fromDomain(product);
        InMemoryPersistence.getProducts().add(dbProduct);
    }
}
