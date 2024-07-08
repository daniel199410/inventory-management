package dcatano.domain.product.creation;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventPublisher;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import dcatano.domain.product.ValidationError;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ProductCreator {
    private final ProductRepository productRepository;
    private final EventPublisher<Product> eventPublisher;

    public List<String> create(ProductCreatorDTO productCreatorDTO) {
        List<ValidationError> validationErrors = validateProduct(productCreatorDTO);
        if(!validationErrors.isEmpty()) {
            return validationErrors.stream().map(ValidationError::reason).toList();
        }
        Product product = productCreatorDTO.toProduct();
        productRepository.save(product);
        eventPublisher.publish(EventType.CREATION, new Event<>(product));
        return Collections.emptyList();
    }

    private List<ValidationError> validateProduct(ProductCreatorDTO productCreatorDTO) {
        List<ValidationError> validationErrors = new LinkedList<>();
        if(Optional.ofNullable(productCreatorDTO.name()).orElse("").isEmpty()) {
            validationErrors.add(new ValidationError(Messages.INVALID_NAME.getMessage()));
        }
        if(Optional.ofNullable(productCreatorDTO.category()).orElse("").isEmpty()) {
            validationErrors.add(new ValidationError(Messages.INVALID_CATEGORY.getMessage()));
        }
        if(Optional.ofNullable(productCreatorDTO.quantity()).orElse(-1) < 0) {
            validationErrors.add(new ValidationError(Messages.INVALID_QUANTITY.getMessage()));
        }
        if(Optional.ofNullable(productCreatorDTO.price()).orElse(0.0) < 0) {
            validationErrors.add(new ValidationError(Messages.INVALID_PRICE.getMessage()));
        }
        if(productCreatorDTO.threshold() != null && productCreatorDTO.threshold() < 0) {
            validationErrors.add(new ValidationError(Messages.INVALID_THRESHOLD.getMessage()));
        }
        if(productCreatorDTO.threshold() != null && productCreatorDTO.recharge() == null) {
            validationErrors.add(new ValidationError(Messages.INVALID_RECHARGE.getMessage()));
        }
        if(productCreatorDTO.threshold() != null && productCreatorDTO.recharge() != null && productCreatorDTO.recharge() < productCreatorDTO.threshold()) {
            validationErrors.add(new ValidationError(Messages.RECHARGE_LOWER_THAN_THRESHOLD.getMessage()));
        }
        return validationErrors;
    }
}
