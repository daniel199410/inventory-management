package dcatano.domain.product.transaction;

import dcatano.domain.observer.Event;
import dcatano.domain.observer.EventType;
import dcatano.domain.product.ProductMock;

public class TransactionMock {
    public static Transaction create() {
        return new Transaction(EventType.CREATION, new Event<>(ProductMock.create()));
    }
}
