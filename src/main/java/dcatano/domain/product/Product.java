package dcatano.domain.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class Product {
    @ToString.Exclude
    private final UUID id;
    @ToString.Exclude
    private final String name;
    @ToString.Exclude
    private final String category;
    private final Integer quantity;
    @ToString.Exclude
    private final Supply supply;
    @ToString.Exclude
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
