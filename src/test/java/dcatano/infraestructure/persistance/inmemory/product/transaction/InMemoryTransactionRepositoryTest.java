package dcatano.infraestructure.persistance.inmemory.product.transaction;

import dcatano.domain.product.transaction.TransactionMock;
import dcatano.domain.product.transaction.TransactionRepository;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class InMemoryTransactionRepositoryTest {
    private final TransactionRepository transactionRepository = new InMemoryTransactionRepository();

    @Test
    void shouldSaveTransaction() {
        transactionRepository.save(TransactionMock.create());
        assertFalse(InMemoryPersistence.getTransactions().isEmpty());
    }
}
