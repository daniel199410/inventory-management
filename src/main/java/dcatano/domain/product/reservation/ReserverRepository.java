package dcatano.domain.product.reservation;

import java.util.Optional;
import java.util.UUID;

public interface ReserverRepository {
    void save(Reservation reservation);

    Optional<Reservation> findByProductId(UUID id);
}
