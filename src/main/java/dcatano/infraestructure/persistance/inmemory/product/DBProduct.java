package dcatano.infraestructure.persistance.inmemory.product;

import dcatano.domain.product.Product;
import dcatano.domain.product.Supply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
public class DBProduct {
    private UUID id;
    private String name;
    private String category;
    private Integer quantity;
    private Integer recharge;
    private Integer threshold;
    private Double price;
    @Setter
    private UUID version;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DBProduct dbProduct)) return false;
        return Objects.equals(id, dbProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
