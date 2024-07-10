package dcatano.domain.product.search;

import dcatano.domain.product.Product;

import java.util.UUID;

public record ProductSearchDTO(UUID id, String name, String category, Integer quantity, Double price) {
    public static ProductSearchDTO fromDomain(Product product) {
        return new ProductSearchDTO(
            product.getId(),
            product.getName(),
            product.getCategory(),
            product.getQuantity(),
            product.getPrice()
        );
    }
}
