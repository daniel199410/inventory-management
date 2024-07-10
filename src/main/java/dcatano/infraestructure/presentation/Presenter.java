package dcatano.infraestructure.presentation;

import dcatano.infraestructure.presentation.console.Options;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class Presenter {
    private final Presentation presentation;

    public void present() {
        Optional<Options> selectedOption;
        presentation.showWelcome();
        while (true) {
            presentation.showOptions();
            selectedOption = presentation.selectOption();
            if(selectedOption.isEmpty()) {
                continue;
            }
            if(Options.EXIT.equals(selectedOption.get())) {
                presentation.exit();
                return;
            }
            presentation.executeAction(selectedOption.get());
        }
    }
}
