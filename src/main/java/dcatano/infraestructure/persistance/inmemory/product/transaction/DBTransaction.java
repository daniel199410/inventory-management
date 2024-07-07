package dcatano.infraestructure.persistance.inmemory.product.transaction;

import dcatano.domain.product.transaction.Transaction;
import dcatano.domain.product.transaction.TransactionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DBTransaction {
    private final UUID id;
    private final TransactionType transactionType;
    private final UUID productId;
    private final Instant creationDate;


    public static DBTransaction fromDomain(Transaction transaction) {
        return new DBTransaction(
            transaction.getId(),
            transaction.getTransactionType(),
            transaction.getProduct().getId(),
            transaction.getCreationDate()
        );
    }
}
