package dcatano.infraestructure.persistance.inmemory.product.reservation;

import dcatano.domain.product.reservation.Reservation;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryReserverRepositoryTest {
    private final InMemoryReserverRepository inMemoryReserverRepository = new InMemoryReserverRepository();

    @Test
    void shouldDeleteProduct() {
        UUID uuid = UUID.randomUUID();
        inMemoryReserverRepository.save(new Reservation(uuid, 100));
        Optional<Reservation> reservation = inMemoryReserverRepository.findByProductId(uuid);
        assertTrue(reservation.isPresent());
        inMemoryReserverRepository.delete(new Reservation(uuid, 100));
        assertTrue(InMemoryPersistence.getReservations().isEmpty());
    }
}
