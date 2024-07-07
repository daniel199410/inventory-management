package dcatano.infraestructure.persistance.inmemory.product;

import dcatano.domain.product.Product;

import java.util.UUID;

public record DBProduct(UUID id, String name, String category, Integer quantity, Integer threshold, Double price,
                        UUID version) {

    public static DBProduct fromDomain(Product product) {
        return new DBProduct(
            product.getId(),
            product.getName(),
            product.getCategory(),
            product.getQuantity(),
            product.getThreshold(),
            product.getPrice(),
            product.getVersion()
        );
    }

    public Product toDomain() {
        return new Product(
            this.id,
            this.name,
            this.category,
            this.quantity,
            this.threshold,
            this.price,
            this.version
        );
    }
}
