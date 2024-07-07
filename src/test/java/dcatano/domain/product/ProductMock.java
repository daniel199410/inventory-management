package dcatano.domain.product;

public class ProductMock {
    public static Product create() {
        return new Product("TV", "Electrodomésticos", 1000, 10, 2_500_000.0);
    }
}
