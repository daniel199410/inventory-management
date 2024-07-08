package dcatano.domain.product.creation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
enum Messages {
    INVALID_CATEGORY("La categoría no puede ser vacía"),
    INVALID_NAME("El nombre no puede ser vacío"),
    INVALID_PRICE("El precio debe ser mayor o igual a cero"),
    INVALID_QUANTITY("La cantidad debe ser mayor o igual a 0"),
    INVALID_RECHARGE("Si deseas asignar un umbral de recarga automático, debes definir de cuánto va a ser la recarga automática"),
    INVALID_THRESHOLD("El umbral debe estar vacío o ser un número mayor o igual a cero."),
    RECHARGE_LOWER_THAN_THRESHOLD("El valor automático de recarga debe ser mayor o igual al umbral.");

    private final String message;
}
