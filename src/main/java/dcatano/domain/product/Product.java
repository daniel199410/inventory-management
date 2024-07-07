package dcatano.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Product {
    private final UUID id;
    private final String name;
    private final String category;
    private final Integer quantity;
    private final Integer threshold;
    private final Double price;
    private final UUID version;

    public Product(String name, String category, Integer quantity, Integer threshold, Double price) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.threshold = threshold;
        this.price = price;
        this.version = UUID.randomUUID();
    }
}
