package dcatano.infraestructure.presentation.console;

import dcatano.domain.observer.EventType;
import dcatano.domain.product.creation.IProductCreator;
import dcatano.domain.product.creation.ProductCreatorDTO;
import dcatano.domain.product.search.IProductSearchEngine;
import dcatano.domain.product.search.PriceRangeFilter;
import dcatano.domain.product.search.ProductPriceRangeException;
import dcatano.domain.product.search.ProductSearchDTO;
import dcatano.domain.product.search.ProductSearchFilters;
import dcatano.domain.product.update.IProductUpdater;
import dcatano.domain.product.update.ProductUpdateDTO;
import dcatano.infraestructure.presentation.Presentation;
import lombok.RequiredArgsConstructor;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class Console implements Presentation {
    private final IProductCreator productCreator;
    private final IProductUpdater productUpdater;
    private final IProductSearchEngine productSearchEngine;

    @Override
    public void executeAction(Options option) {
        switch (option) {
            case ADD_PRODUCT -> presentProductCreation();
            case UPDATE_PRODUCT -> presentProductUpdate();
            case LIST_PRODUCTS -> presentProductSearch();
        }
    }

    private void presentProductSearch() {
        Scanner scanner = new Scanner(System.in);
        ProductSearchFilters.ProductSearchFiltersBuilder filter = ProductSearchFilters.builder();
        System.out.print("¿desea hacer una búsqueda filtrada (Y)? ");
        try {
            boolean filterQuery = scanner.nextLine().equalsIgnoreCase("Y");
            if(filterQuery) {
                System.out.print("¿desea filtrar por categoría (Y)? ");
                if(scanner.nextLine().equalsIgnoreCase("Y")) {
                    System.out.print(Messages.ENTER_CATEGORY.getMessage());
                    filter.category(scanner.nextLine());
                }
                System.out.print("¿desea filtrar por rango de precio (Y)? ");
                if(scanner.nextLine().equalsIgnoreCase("Y")) {
                    System.out.print(Messages.ENTER_LOWER_PRICE_RANGE_BOUND.getMessage());
                    double lowerBound = scanner.nextDouble();
                    System.out.print(Messages.ENTER_UPPER_PRICE_RANGE_BOUND.getMessage());
                    double upperBound = scanner.nextDouble();
                    filter.priceRangeFilter(new PriceRangeFilter(lowerBound, upperBound));
                }
            }
            List<ProductSearchDTO> searchResult = productSearchEngine.findBy(filter.build()).get();
            if(searchResult.isEmpty()) {
                System.out.println("No se han encontrado registros");
                return;
            }
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.of("es", "CO"));
            for (ProductSearchDTO item : searchResult) {
                System.out.printf("id: %s, Nombre: %s, Categoría: %s, Cantidad: %d, Precio: %s%n", item.id(), item.name(), item.category(), item.quantity(), numberFormat.format(item.price()));
                System.out.println(Messages.DIVIDER.getMessage());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Ha ocurrido un error consultando los productos. Intenta más tarde.");
        } catch (ProductPriceRangeException e) {
            System.err.printf("No se ha podido establecer el rango de precios. Razón: %s%n", e.getMessage());
        }
    }

    private void presentProductUpdate() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print(Messages.ENTER_PRODUCT_ID.getMessage());
            UUID productId = UUID.fromString(scanner.nextLine());
            System.out.print(Messages.ENTER_QUANTITY.getMessage());
            int quantity = scanner.nextInt();
            System.out.print(Messages.ENTER_PRICE.getMessage());
            double price = scanner.nextDouble();
            List<String> failedValidations = productUpdater.updateQuantityAndPrice(new ProductUpdateDTO(productId, quantity, price), EventType.UPDATE).get();
            if(failedValidations.isEmpty()) {
                System.out.println(Messages.PRODUCT_CREATED.getMessage());
                return;
            }
            shouldFailureReasons(Messages.PRODUCT_NOT_UPDATED.getMessage(), failedValidations);
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("No se ha podido actualizar el producto.");
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.err.println(Messages.ENTER_CORRECT_DATA.getMessage());
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
            shouldFailureReasons(Messages.PRODUCT_NOT_CREATED.getMessage(), failedValidations);
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

    private void shouldFailureReasons(String message, List<String> failedValidations) {
        System.err.println(message);
        failedValidations.forEach(v -> System.err.printf("-- %s%n", v));
    }
}
