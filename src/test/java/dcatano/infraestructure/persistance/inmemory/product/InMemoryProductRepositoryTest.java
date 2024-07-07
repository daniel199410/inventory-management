package dcatano.infraestructure.persistance.inmemory.product;

import dcatano.domain.product.ProductMock;
import dcatano.domain.product.ProductRepository;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class InMemoryProductRepositoryTest {
    private final ProductRepository productRepository = new InMemoryProductRepository();

    @Test
    void shouldSaveProduct() {
        productRepository.save(ProductMock.create());
        assertFalse(InMemoryPersistence.getProducts().isEmpty());
    }
}
