package dcatano.domain.product.search;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Messages {
    NULL_RANGE("El rango debe tener almenos un límite inferior o superior."),
    INVALID_RANGE("El rango inferior es mayor que el superior");

    private final String message;
}
