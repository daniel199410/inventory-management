package dcatano.infraestructure.persistance.inmemory.product.transaction;

import dcatano.domain.product.transaction.Transaction;
import dcatano.domain.product.transaction.TransactionType;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

public record DBTransaction(UUID id, TransactionType transactionType, UUID productId, Instant creationDate) {
    public static DBTransaction fromDomain(Transaction transaction) {
        return new DBTransaction(
            transaction.getId(),
            transaction.getTransactionType(),
            transaction.getProduct().getId(),
            transaction.getCreationDate()
        );
    }
}
