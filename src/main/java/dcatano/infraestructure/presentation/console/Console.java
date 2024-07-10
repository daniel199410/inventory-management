package dcatano.infraestructure.presentation.console;

import dcatano.domain.product.creation.IProductCreator;
import dcatano.domain.product.creation.ProductCreatorDTO;
import dcatano.infraestructure.presentation.Presentation;
import lombok.RequiredArgsConstructor;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RequiredArgsConstructor
public class Console implements Presentation {
    private final IProductCreator productCreator;

    @Override
    public void executeAction(Options option) {
        switch (option) {
            case ADD_PRODUCT -> presentProductCreation();
        }
    }

    private void presentProductCreation() {
        Scanner scanner = new Scanner(System.in);
        Integer threshold = null;
        Integer recharge = null;
        try {
            System.out.print(Messages.ENTER_NAME.getMessage());
            String name = scanner.nextLine();
            System.out.print(Messages.ENTER_CATEGORY.getMessage());
            String category = scanner.nextLine();
            System.out.print(Messages.ENTER_QUANTITY.getMessage());
            int quantity = Integer.parseInt(scanner.nextLine());
            System.out.print(Messages.AUTO_SUPPLY.getMessage());
            String text = scanner.nextLine();
            boolean needSupply = text.equalsIgnoreCase("Y");
            if(needSupply) {
                System.out.print(Messages.ENTER_THRESHOLD.getMessage());
                threshold = scanner.nextInt();
                System.out.print(Messages.ENTER_RECHARGE.getMessage());
                recharge = scanner.nextInt();
            }
            System.out.print(Messages.ENTER_PRICE.getMessage());
            double price = scanner.nextDouble();
            List<String> failedValidations = productCreator.create(new ProductCreatorDTO(name, category, quantity, recharge, threshold, price));
            if(failedValidations.isEmpty()) {
                System.out.println(Messages.PRODUCT_CREATED.getMessage());
                return;
            }
            System.err.println(Messages.PRODUCT_NOT_CREATED.getMessage());
            failedValidations.forEach(v -> System.err.printf("-- %s%n", v));
        } catch (InputMismatchException | NumberFormatException e) {
            System.err.println(Messages.ENTER_CORRECT_DATA.getMessage());
        }
    }

    @Override
    public void exit() {
        System.out.println(Messages.BYE.getMessage());
    }

    @Override
    public void showWelcome() {
        System.out.println(Messages.HELLO.getMessage());
    }

    @Override
    public void showOptions() {
        System.out.println(Messages.OPTIONS_INTRODUCTION.getMessage());
        for (Options option : Options.values()) {
            System.out.printf("%d. %s%n", option.ordinal() + 1, option.getDescription());
        }
    }

    @Override
    public Optional<Options> selectOption() {
        Scanner scanner = new Scanner(System.in);
        try {
            return Optional.of(Options.values()[scanner.nextInt() - 1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(Messages.SELECT_CORRECT_OPTION.getMessage());
            return Optional.empty();
        } catch (InputMismatchException e) {
            System.err.println(Messages.ENTER_CORRECT_DATA.getMessage());
            return Optional.empty();
        }
    }
}
