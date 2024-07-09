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
    public synchronized void save(Product product) throws OptimisticLockException {
        DBProduct dbProduct = DBProduct.fromDomain(product);
        Optional<Product> optionalProduct = findById(product.getId());
        if(optionalProduct.isPresent() && !optionalProduct.get().getVersion().equals(product.getVersion())) {
            throw new OptimisticLockException();
        }
        dbProduct.setVersion(UUID.randomUUID());
        InMemoryPersistence.getProducts().put(dbProduct.getId(), dbProduct);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(InMemoryPersistence.getProducts().get(id)).map(DBProduct::toDomain);
    }

    @Override
    public List<Product> findByFilter(ProductSearchFilters productSearchFilters) {
        return InMemoryPersistence.getProducts().values().stream()
            .filter(isEqualCategory(productSearchFilters.category()))
            .filter(isBetweenPriceRange(productSearchFilters.priceRangeFilter()))
        .map(DBProduct::toDomain).toList();
    }

    @Override
    public void delete(Product product) {
        InMemoryPersistence.getProducts().remove(product.getId());
    }

    private static Predicate<DBProduct> isEqualCategory(String category) {
        return product -> {
            if(category != null) {
                return category.equals(product.getCategory());
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
        return product -> product.getPrice() <= upperBound;
    }

    private static Predicate<DBProduct> isPriceGreaterThanOrEqual(Double lowerBound) {
        return product -> product.getPrice() >= lowerBound;
    }
}
