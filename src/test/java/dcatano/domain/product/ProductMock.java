package dcatano.domain.product;

import java.util.UUID;

public class ProductMock {
    public static Product create() {
        return new Product("TV", "Electrodomésticos", 1000, 10, 2_500_000.0);
    }

    public static Product create(UUID uuid) {
        return new Product(uuid, "TV", "Electrodomésticos", 1000, 10, 2_500_000.0, uuid);
    }
}
