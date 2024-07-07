package dcatano.domain.product.creation;

import dcatano.domain.product.Product;

public record ProductCreatorDTO(
    String name,
    String category,
    Integer quantity,
    Integer threshold,
    Double price
) {
    public Product toProduct() {
        return new Product(this.name, this.category, this.quantity, this.threshold, this.price);
    }
}
