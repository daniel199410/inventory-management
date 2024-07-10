package dcatano.infraestructure.presentation.console;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Options {
    ADD_PRODUCT("Crear producto"),
    EXIT("Salir"),
    UPDATE_PRODUCT("Actualizar producto"),
    LIST_PRODUCTS("Listar todos los productos"),
    SEARCH_PRODUCTS_BY_CATEGORY_OR_PRICE_RANGE("Buscar productos por categoría o precio"),
    ;

    private final String description;

}
