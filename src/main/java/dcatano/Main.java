package dcatano;

import dcatano.domain.observer.EventType;
import dcatano.domain.observer.product.ProductEvent;
import dcatano.domain.product.ProductRepository;
import dcatano.domain.product.creation.ProductCreator;
import dcatano.domain.product.creation.ProductCreatorDTO;
import dcatano.domain.product.transaction.TransactionProductListener;
import dcatano.domain.product.transaction.TransactionRepository;
import dcatano.infraestructure.persistance.inmemory.product.InMemoryProductRepository;
import dcatano.infraestructure.persistance.inmemory.product.transaction.InMemoryTransactionRepository;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        ProductEvent productEvent = new ProductEvent();
        productEvent.getEventManager().subscribe(EventType.CREATION, new TransactionProductListener(transactionRepository));
        ProductCreator productCreator = new ProductCreator(productRepository, productEvent);
        productCreator.create(new ProductCreatorDTO("a", "a", 1, 1, 1.0));
        System.out.println("Hello world!");
    }
}