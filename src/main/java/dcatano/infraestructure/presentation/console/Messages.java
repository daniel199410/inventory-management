package dcatano.infraestructure.presentation.console;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Messages {
    AUTO_SUPPLY("¿deseas que el producto se abastezca automáticamente? (Escribe Y en caso de ser afirmativo): "),
    BYE("Adiós"),
    DIVIDER("------------------------------------------------------------------------------------------------------"),
    ENTER_CATEGORY("Ingresa la categoría: "),
    ENTER_CORRECT_DATA("Ingresa los datos correctos"),
    ENTER_LOWER_PRICE_RANGE_BOUND("Ingresa el rango inferior de precios: "),
    ENTER_NAME("Ingresa el nombre: "),
    ENTER_PRICE("Ingresa el precio: "),
    ENTER_PRODUCT_ID("Ingresa el identificador del producto: "),
    ENTER_QUANTITY("Ingresa la cantidad: "),
    ENTER_RECHARGE("Ingresa en cuánto se debe reabastecer el producto cuando alcance el umbral de auto abastecimiento: "),
    ENTER_THRESHOLD("Ingresa la cantidad para la cual cuando llegue el producto a esta se reabastezca: "),
    ENTER_UPPER_PRICE_RANGE_BOUND("Ingresa el rango superior de precios: "),
    HELLO("""
        ------------------------------------------------
        -      Bienvenido al sistema de inventario     -
        ------------------------------------------------
        """),
    OPTIONS_INTRODUCTION("Digita una opción para continuar y luego haz enter..."),
    PRODUCT_CREATED("Producto creado exitosamente"),
    PRODUCT_NOT_CREATED("El producto no se ha podido crear. Razón: "),
    PRODUCT_NOT_UPDATED("Producto no actualizado"),
    SELECT_CORRECT_OPTION("Selecciona una opción correcta"),
    ;

    private final String message;
}
