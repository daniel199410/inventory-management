package dcatano.infraestructure.presentation;

import dcatano.infraestructure.presentation.console.Options;

import java.util.Optional;

public interface Presentation {
    void executeAction(Options option);
    void exit();
    void showWelcome();
    void showOptions();
    Optional<Options> selectOption();
}
