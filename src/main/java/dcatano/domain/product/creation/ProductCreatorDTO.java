package dcatano.domain.product.creation;

import dcatano.domain.product.Product;
import dcatano.domain.product.Supply;

public record ProductCreatorDTO(
    String name,
    String category,
    Integer quantity,
    Integer recharge,
    Integer threshold,
    Double price
) {
    public Product toProduct() {
        return new Product(this.name, this.category, this.quantity, new Supply(threshold, recharge), this.price);
    }
}
