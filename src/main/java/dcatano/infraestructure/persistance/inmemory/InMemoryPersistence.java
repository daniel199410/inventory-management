package dcatano.infraestructure.persistance.inmemory;
import dcatano.infraestructure.persistance.inmemory.product.DBProduct;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class InMemoryPersistence {
    @Getter
    static final Set<DBProduct> products = new HashSet<>();
}
