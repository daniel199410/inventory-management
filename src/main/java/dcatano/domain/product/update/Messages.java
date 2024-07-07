package dcatano.domain.product.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum Messages {
    INVALID_PRICE("El precio debe ser mayor o igual a cero"),
    INVALID_QUANTITY("La cantidad debe ser mayor o igual a 0"),
    INVALID_PRICE_AND_QUANTITY("Debes enviar almenos la cantidad o el precio"),
    PRODUCT_NOT_FOUND("No se ha encontrado el producto"),
    ;

    private final String message;
}
