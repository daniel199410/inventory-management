package dcatano.infraestructure.persistance.inmemory.product.transaction;

import dcatano.domain.product.transaction.Transaction;
import dcatano.domain.product.transaction.TransactionRepository;
import dcatano.infraestructure.persistance.inmemory.InMemoryPersistence;

public class InMemoryTransactionRepository implements TransactionRepository {
    @Override
    public void save(Transaction transaction) {
        DBTransaction dbTransaction = DBTransaction.fromDomain(transaction);
        InMemoryPersistence.getTransactions().add(dbTransaction);
    }
}
