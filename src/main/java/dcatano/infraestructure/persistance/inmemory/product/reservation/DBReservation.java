package dcatano.infraestructure.persistance.inmemory.product.reservation;

import dcatano.domain.product.reservation.Reservation;

import java.util.UUID;

public record DBReservation(UUID productId, Integer quantity) {

    public static DBReservation fromDomain(Reservation reservation) {
        return new DBReservation(reservation.productId(), reservation.quantity());
    }
}
