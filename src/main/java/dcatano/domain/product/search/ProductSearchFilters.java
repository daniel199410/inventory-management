package dcatano.domain.product.search;

import lombok.Builder;

@Builder
public record ProductSearchFilters(String category, PriceRangeFilter priceRangeFilter) {

}
