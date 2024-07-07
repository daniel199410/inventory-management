package dcatano.domain.product.search;

import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class ProductSearchEngine {
    private final ProductRepository productRepository;

    public CompletableFuture<List<ProductSearchDTO>> findBy(ProductSearchFilters productSearchFilters) {
        return CompletableFuture.supplyAsync(() -> {
            List<Product> products = productRepository.findByFilter(productSearchFilters);
            return products.stream().map(ProductSearchDTO::fromDomain).toList();
        });
    }
}
