package dcatano.domain.product.transaction;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventListener;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionProductListener implements EventListener<Product> {
    private final TransactionRepository transactionRepository;

    @Override
    public void update(EventType eventType, Event<Product> product) {
        transactionRepository.save(new Transaction(eventType, product));
        System.out.println("Transacción registrada");
    }
}
