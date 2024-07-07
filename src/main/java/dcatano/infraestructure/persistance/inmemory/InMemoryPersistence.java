package dcatano.infraestructure.persistance.inmemory;
import dcatano.infraestructure.persistance.inmemory.product.DBProduct;
import dcatano.infraestructure.persistance.inmemory.product.transaction.DBTransaction;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class InMemoryPersistence {
    @Getter
    static final Set<DBProduct> products = new HashSet<>();
    @Getter
    static final Set<DBTransaction> transactions = new HashSet<>();
}
