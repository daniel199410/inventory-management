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
    FILTER_BY_CATEGORY_QUESTION("¿desea filtrar por categoría (Y)? "),
    FILTER_BY_PRICE_QUESTION("¿desea filtrar por rango de precio (Y)? "),
    HELLO("""
        ------------------------------------------------
        -      Bienvenido al sistema de inventario     -
        ------------------------------------------------
        """),
    LIBERATION_FAILURE("No se ha podido liberar la reserva. Razón: "),
    OPTIONS_INTRODUCTION("Digita una opción para continuar y luego haz enter..."),
    PRODUCT_CREATED("Producto creado exitosamente"),
    PRODUCT_DESCRIPTION("id: %s, Nombre: %s, Categoría: %s, Cantidad: %d, Precio: %s%n"),
    PRODUCT_NOT_CREATED("El producto no se ha podido crear. Razón: "),
    PRODUCT_NOT_UPDATED("Producto no actualizado"),
    PRODUCT_PRICE_RANGE_ERROR("No se ha podido establecer el rango de precios. Razón: %s%n"),
    PRODUCT_RESERVATION_HERO("""
            ¿qué deseas hacer?
            1. Reservación.
            2. Liberación.
            """),
    PRODUCT_SEARCH_ERROR("Ha ocurrido un error consultando los productos. Intenta más tarde."),
    REGISTRIES_NOT_FOUND("No se han encontrado registros"),
    RESERVATION_FAILURE("No se ha podido ejecutar la reserva. Razón:"),
    RESERVATION_GENERAL_ERROR("Ha ocurrido un error haciendo la reserva de inventario. Intenta más tarde."),
    RESERVATION_OPTIONS("Digita 1 o 2."),
    SEARCH_TYPES_DESCRIPTION("¿desea hacer una búsqueda filtrada (Y)? "),
    SELECT_CORRECT_OPTION("Selecciona una opción correcta"),
    SUCCESSFUL_LIBERATION("Se ha hecho la liberación de inventario exitosamente"),
    SUCCESSFUL_RESERVATION("Se ha hecho la reserva de inventario correctamente."),
    ;

    private final String message;
}
