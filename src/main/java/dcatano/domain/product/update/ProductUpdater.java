package dcatano.domain.product.update;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventPublisher;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import dcatano.domain.product.Supply;
import dcatano.domain.product.ValidationError;
import dcatano.infraestructure.persistance.inmemory.product.OptimisticLockException;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class ProductUpdater implements IProductUpdater{
    private final ProductRepository productRepository;
    private final EventPublisher<Product> eventPublisher;

    public CompletableFuture<List<String>> updateQuantityAndPrice(ProductUpdateDTO productUpdateDTO, EventType eventType) {
        return CompletableFuture.supplyAsync(() -> {
            while (true) {
                List<ValidationError> validationErrors = validateUpdateFields(productUpdateDTO);
                if(!validationErrors.isEmpty()) {
                    return validationErrors.stream().map(ValidationError::reason).toList();
                }
                Optional<Product> optionalProduct = productRepository.findById(productUpdateDTO.id());
                if(optionalProduct.isEmpty()) {
                    return List.of(Messages.PRODUCT_NOT_FOUND.getMessage());
                }
                Product product = new Product(
                    optionalProduct.get().getId(),
                    optionalProduct.get().getName(),
                    optionalProduct.get().getCategory(),
                    updateQuantity(optionalProduct.get(), productUpdateDTO),
                    Optional.ofNullable(optionalProduct.get().getSupply()).map(supply -> new Supply(supply.threshold(), supply.recharge())).orElse(null),
                    Optional.ofNullable(productUpdateDTO.price()).orElse(optionalProduct.get().getPrice()),
                    optionalProduct.get().getVersion()
                );
                try {
                    productRepository.save(product);
                    eventPublisher.publish(eventType, new Event<>(product));
                    return Collections.emptyList();
                } catch (OptimisticLockException ignored) {
                }
            }
        });
    }

    private Integer updateQuantity(Product product, ProductUpdateDTO productUpdateDTO) {
        if(productUpdateDTO.replace()) {
            return Optional.ofNullable(productUpdateDTO.quantity()).orElse(product.getQuantity());
        }
        return product.getQuantity() + Optional.ofNullable(productUpdateDTO.quantity()).orElse(0);
    }

    private List<ValidationError> validateUpdateFields(ProductUpdateDTO productUpdateDTO) {
        List<ValidationError> validationErrors = new LinkedList<>();
        if(productUpdateDTO.price() == null && productUpdateDTO.quantity() == null) {
            validationErrors.add(new ValidationError(Messages.INVALID_PRICE_AND_QUANTITY.getMessage()));
        }
        return validationErrors;
    }
}
