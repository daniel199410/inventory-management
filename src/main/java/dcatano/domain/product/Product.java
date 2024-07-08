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
    private final Supply supply;
    private final Double price;
    private final UUID version;

    public Product(String name, String category, Integer quantity, Supply supply, Double price) {
        this.supply = supply;
        this.id = UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.version = UUID.randomUUID();
    }
}
