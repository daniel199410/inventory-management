package dcatano.domain.product.transaction;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Transaction {
    private final UUID id;
    private final TransactionType transactionType;
    private final Product product;
    private final Instant creationDate;

    public Transaction(EventType eventType, Event<Product> event) {
        this.id = UUID.randomUUID();
        this.product = event.getPayload();
        this.creationDate = event.getCreationDate();
        this.transactionType = mapToTransactionType(eventType);
    }

    private TransactionType mapToTransactionType(EventType eventType) {
        return switch (eventType) {
            case UPDATE -> TransactionType.UPDATE;
            case CREATION -> TransactionType.CREATION;
            case ELIMINATION -> TransactionType.ELIMINATION;
        };
    }
}
