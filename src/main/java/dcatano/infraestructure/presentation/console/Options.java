package dcatano.infraestructure.presentation.console;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Options {
    ADD_PRODUCT("Crear producto"),
    UPDATE_PRODUCT("Actualizar producto"),
    LIST_PRODUCTS("Listar todos los productos"),
    RESERVATION("Hacer/Liberar reserva"),
    EXIT("Salir"),
    ;

    private final String description;

}
