package dcatano.domain.product.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Messages {
    INVALID_QUANTITY("La cantidad no es válida"),
    PRODUCT_NOT_FOUND("Producto no encontrado para reservar"),
    PRODUCT_QUANTITY_NOT_ENOUGH("La cantidad actual del producto es menor a lo que se desea reservar."),
    QUANTITY_LOWER_THAN_ZERO("La cantidad a reservar debe ser mayor a 0"),
    RESERVATION_NOT_CREATED("No se ha podido ejecutar la reserva, intenta más tarde."),
    RESERVATION_NOT_FOUND("No se encontró una reserva de inventario para el producto"),
    ;

    private final String message;
}
