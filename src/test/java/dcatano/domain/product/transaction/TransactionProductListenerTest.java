package dcatano.domain.product.transaction;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventManager;
import dcatano.domain.observer.EventPublisher;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.Product;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TransactionProductListenerTest {
    private final TransactionRepository transactionRepository = new TransactionRepository() {
        @Override
        public void save(Transaction transaction) {}
    };
    private final TransactionRepository transactionRepositorySpy = spy(transactionRepository);
    private final TransactionProductListener transactionProductListener = new TransactionProductListener(transactionRepositorySpy);

    @Test
    void shouldListenAndSaveEventAsATransaction() throws InterruptedException {
        TestEvent eventPublisher = new TestEvent();
        eventPublisher.getEventManager().subscribe(EventType.CREATION, transactionProductListener);
        eventPublisher.publish(EventType.CREATION, new Event<>(new Product("", "", 1, null, 1.0)));
        Thread.sleep(500);
        verify(transactionRepositorySpy, times(1)).save(Mockito.any(Transaction.class));
    }
}

@Getter
class TestEvent implements EventPublisher<Product> {
    private final EventManager<Product> eventManager;

    public TestEvent() {
        eventManager = new EventManager<>();
    }

    @Override
    public void publish(EventType eventType, Event<Product> event) {
        eventManager.notify(eventType, event);
    }
}