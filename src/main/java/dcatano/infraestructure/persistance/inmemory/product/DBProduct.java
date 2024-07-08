package dcatano.infraestructure.persistance.inmemory.product;

import dcatano.domain.product.Product;
import dcatano.domain.product.Supply;

import java.util.UUID;

public record DBProduct(UUID id, String name, String category, Integer quantity, Integer recharge, Integer threshold, Double price,
                        UUID version) {

    public static DBProduct fromDomain(Product product) {
        return new DBProduct(
            product.getId(),
            product.getName(),
            product.getCategory(),
            product.getQuantity(),
            product.getSupply().recharge(),
            product.getSupply().threshold(),
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
            new Supply(threshold, recharge),
            this.price,
            this.version
        );
    }
}
