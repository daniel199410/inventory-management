package dcatano.infraestructure.persistance.inmemory;
import dcatano.infraestructure.persistance.inmemory.product.DBProduct;
import dcatano.infraestructure.persistance.inmemory.product.reservation.DBReservation;
import dcatano.infraestructure.persistance.inmemory.product.transaction.DBTransaction;
import lombok.Getter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryPersistence {
    @Getter
    static Map<UUID, DBProduct> products = new ConcurrentHashMap<>();
    @Getter
    static final Set<DBTransaction> transactions = new HashSet<>();
    @Getter
    static final Set<DBReservation> reservations = new HashSet<>();
}
