package dcatano.domain.product.reservation;

import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import dcatano.domain.product.ProductRepository;
import dcatano.domain.product.ValidationError;
import dcatano.domain.product.update.ProductUpdateDTO;
import dcatano.domain.product.update.ProductUpdater;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
public class Reserver {
    private final ProductRepository productRepository;
    private final ProductUpdater productUpdater;
    private final ReserverRepository reserverRepository;

    public CompletableFuture<List<String>> reserveQuantity(ReserveQuantityDTO reserveQuantityDTO) {
        return CompletableFuture.supplyAsync(() -> {
            List<ValidationError> validationErrors = validateUpdateFields(reserveQuantityDTO);
            if(!validationErrors.isEmpty()) {
                return validationErrors.stream().map(ValidationError::reason).toList();
            }
            Optional<Product> optionalProduct = productRepository.findById(reserveQuantityDTO.productId());
            if(optionalProduct.isEmpty()) {
                return List.of(Messages.PRODUCT_NOT_FOUND.getMessage());
            }
            if(optionalProduct.get().getQuantity() < reserveQuantityDTO.quantity()) {
                return List.of(Messages.PRODUCT_QUANTITY_NOT_ENOUGH.getMessage());
            }
            try {
                processReservation(optionalProduct.get(), reserveQuantityDTO.quantity());
            } catch (ExecutionException | InterruptedException e) {
                return Collections.singletonList(Messages.RESERVATION_NOT_CREATED.getMessage());
            }
            return Collections.emptyList();
        });
    }

    public CompletableFuture<List<String>> releaseReservation(UUID productId) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<Reservation> optionalReservation = reserverRepository.findByProductId(productId);
            if(optionalReservation.isEmpty()) {
                return Collections.singletonList(Messages.RESERVATION_NOT_FOUND.getMessage());
            }
            reserverRepository.delete(optionalReservation.get());
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if(optionalProduct.isPresent()) {
                updateProduct(optionalProduct.get(), optionalReservation.get().quantity());
                return Collections.emptyList();
            }
            return Collections.singletonList(Messages.PRODUCT_NOT_FOUND.getMessage());
        });
    }

    private List<ValidationError> validateUpdateFields(@NonNull ReserveQuantityDTO reserveQuantityDTO) {
        List<ValidationError> validationErrors = new LinkedList<>();
        if(reserveQuantityDTO.quantity() == null) {
            validationErrors.add(new ValidationError(Messages.INVALID_QUANTITY.getMessage()));
            return validationErrors;
        }
        if(reserveQuantityDTO.quantity() <= 0) {
            validationErrors.add(new ValidationError(Messages.QUANTITY_LOWER_THAN_ZERO.getMessage()));
        }
        return validationErrors;
    }

    private void processReservation(@NonNull Product product, Integer quantity) throws ExecutionException, InterruptedException {
        boolean updated = updateProduct(product, -quantity).get();
        if(updated) {
            Optional<Reservation> optionalReservation = reserverRepository.findByProductId(product.getId());
            int newQuantity = quantity;
            if(optionalReservation.isPresent()) {
                newQuantity += optionalReservation.get().quantity();
            }
            reserverRepository.save(new Reservation(product.getId(), newQuantity));
        }
    }

    private CompletableFuture<Boolean> updateProduct(Product product, Integer quantity) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<String> result = productUpdater.updateQuantityAndPrice(new ProductUpdateDTO(
                    product.getId(),
                    quantity,
                    product.getPrice()), EventType.RESERVATION
                ).get();
                return result.isEmpty();
            } catch (InterruptedException | ExecutionException e) {
                return false;
            }
        });
    }
}
