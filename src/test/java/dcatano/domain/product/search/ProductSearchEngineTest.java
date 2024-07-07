package dcatano.domain.product.search;

import dcatano.domain.product.Product;
import dcatano.domain.product.ProductMock;
import dcatano.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ProductSearchEngineTest {
    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductSearchEngine productSearchEngine = new ProductSearchEngine(productRepository);

    @Test
    void shouldSearchProducts() throws ProductPriceRangeException, ExecutionException, InterruptedException {
        Product product = ProductMock.create();
        Mockito.when(productRepository.findByFilter(Mockito.any(ProductSearchFilters.class))).thenReturn(Collections.singletonList(product));
        assertEquals(ProductSearchDTO.fromDomain(product), productSearchEngine.findBy(new ProductSearchFilters("", new PriceRangeFilter(0.0, 1.0))).get().getFirst());
    }

    @Test
    void shouldThrowOnNullRangeValues() {
        try {
            new PriceRangeFilter(null, null);
        } catch (ProductPriceRangeException e) {
            assertEquals(Messages.NULL_RANGE.getMessage(), e.getMessage());
        }
    }

    @Test
    void shouldThrowOnLowerBoundGreaterThanUpperBound() {
        try {
            new PriceRangeFilter(2.0, 1.0);
        } catch (ProductPriceRangeException e) {
            assertEquals(Messages.INVALID_RANGE.getMessage(), e.getMessage());
        }
    }
}
