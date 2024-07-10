package dcatano.domain.product.search;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IProductSearchEngine {
    CompletableFuture<List<ProductSearchDTO>> findBy(ProductSearchFilters productSearchFilters);
}
