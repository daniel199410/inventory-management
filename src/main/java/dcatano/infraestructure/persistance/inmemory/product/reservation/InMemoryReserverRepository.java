package dcatano.infraestructure.persistance.inmemory.product.reservation;

import dcatano.domain.product.reservation.Reservation;
import dcatano.domain.product.reservation.ReserverRepository;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;

import java.util.Optional;
import java.util.UUID;

public class InMemoryReserverRepository implements ReserverRepository {
    @Override
    public void save(Reservation reservation) {
        DBReservation dbReservation = DBReservation.fromDomain(reservation);
        InMemoryPersistence.getReservations().add(dbReservation);
    }

    @Override
    public Optional<Reservation> findByProductId(UUID id) {
        return InMemoryPersistence.getReservations()
            .stream()
            .filter(dbReservation -> dbReservation.productId().equals(id))
            .findFirst()
            .map(DBReservation::toDomain);
    }

    @Override
    public void delete(Reservation reservation) {
        InMemoryPersistence.getReservations().remove(DBReservation.fromDomain(reservation));
    }
}
