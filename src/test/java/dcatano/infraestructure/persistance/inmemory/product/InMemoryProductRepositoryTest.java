package dcatano.infraestructure.persistance.inmemory.product;

import dcatano.domain.product.ProductMock;
import dcatano.domain.product.ProductRepository;
import dcatano.domain.product.search.PriceRangeFilter;
import dcatano.domain.product.search.ProductPriceRangeException;
import dcatano.domain.product.search.ProductSearchFilters;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class InMemoryProductRepositoryTest {
    private final ProductRepository productRepository = new InMemoryProductRepository();

    @BeforeAll
    public static void beforeAll() {
        for (int i = 0; i < 100; i++) {
            UUID uuid = UUID.randomUUID();
            InMemoryPersistence.getProducts().put(uuid, new DBProduct(
                uuid,
                "name",
                i % 2 == 0 ? "Categoría 1" : "Categoría 2",
                i,
                null,
                null,
                (double) i,
                UUID.randomUUID()
            ));
        }
    }

    @Test
    void shouldSaveProduct() throws OptimisticLockException {
        productRepository.save(ProductMock.create());
        assertFalse(InMemoryPersistence.getProducts().isEmpty());
    }

    @Test
    void shouldFilterByCategory() {
        assertEquals(50, productRepository.findByFilter(new ProductSearchFilters("Categoría 1", null)).size());
    }

    @Test
    void shouldFetchAllOnNullFilters() {
        assertEquals(100, productRepository.findByFilter(new ProductSearchFilters(null, null)).size());
    }

    @Test
    void shouldFilterByLowerBound() throws ProductPriceRangeException {
        assertEquals(30, productRepository.findByFilter(new ProductSearchFilters(null, new PriceRangeFilter(70.0, null))).size());
    }

    @Test
    void shouldFilterByUpperBound() throws ProductPriceRangeException {
        assertEquals(30, productRepository.findByFilter(new ProductSearchFilters(null, new PriceRangeFilter(null, 29.0))).size());
    }

    @Test
    void shouldFilterByLowerAndUpperBound() throws ProductPriceRangeException {
        assertEquals(41, productRepository.findByFilter(new ProductSearchFilters(null, new PriceRangeFilter(30.0, 70.0))).size());
    }

    @Test
    void shouldFilterByCategoryAndLowerAndUpperBound() throws ProductPriceRangeException {
        assertEquals(6, productRepository.findByFilter(new ProductSearchFilters("Categoría 1", new PriceRangeFilter(0.0, 10.0))).size());
    }
}
