package dcatano.infraestructure.persistance.inmemory.product;

import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import dcatano.domain.product.search.PriceRangeFilter;
import dcatano.domain.product.search.ProductSearchFilters;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

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

    @Override
    public List<Product> findByFilter(ProductSearchFilters productSearchFilters) {
        return InMemoryPersistence.getProducts().parallelStream()
            .filter(isEqualCategory(productSearchFilters.category()))
            .filter(isBetweenPriceRange(productSearchFilters.priceRangeFilter()))
        .map(DBProduct::toDomain).toList();
    }

    private static Predicate<DBProduct> isEqualCategory(String category) {
        return product -> {
            if(category != null) {
                return category.equals(product.category());
            }
            return true;
        };
    }

    private static Predicate<DBProduct> isBetweenPriceRange(PriceRangeFilter priceRangeFilter) {
        if(priceRangeFilter != null && priceRangeFilter.getLowerBound() != null && priceRangeFilter.getUpperBound() != null) {
            return isPriceGreaterThanOrEqual(priceRangeFilter.getLowerBound()).and(isPriceLowerThanOrEqual(priceRangeFilter.getUpperBound()));
        }
        if(Optional.ofNullable(priceRangeFilter).map(PriceRangeFilter::getLowerBound).isPresent()) {
            return isPriceGreaterThanOrEqual(priceRangeFilter.getLowerBound());
        }
        if(Optional.ofNullable(priceRangeFilter).map(PriceRangeFilter::getUpperBound).isPresent()) {
            return isPriceLowerThanOrEqual(priceRangeFilter.getUpperBound());
        }
        return product -> true;
    }

    private static Predicate<DBProduct> isPriceLowerThanOrEqual(Double upperBound) {
        return product -> product.price() <= upperBound;
    }

    private static Predicate<DBProduct> isPriceGreaterThanOrEqual(Double lowerBound) {
        return product -> product.price() >= lowerBound;
    }
}
