package dcatano;

import dcatano.domain.observer.EventType;
import dcatano.domain.observer.product.ProductEvent;
import dcatano.domain.product.ProductRepository;
import dcatano.domain.product.creation.IProductCreator;
import dcatano.domain.product.creation.ProductCreator;
import dcatano.domain.product.elimination.ProductQuantityListener;
import dcatano.domain.product.transaction.TransactionProductListener;
import dcatano.domain.product.transaction.TransactionRepository;
import dcatano.infraestructure.persistance.inmemory.product.InMemoryProductRepository;
import dcatano.infraestructure.persistance.inmemory.product.transaction.InMemoryTransactionRepository;
import dcatano.infraestructure.presentation.Presentation;
import dcatano.infraestructure.presentation.Presenter;
import dcatano.infraestructure.presentation.console.Console;

public class Main {
    static ProductRepository productRepository = new InMemoryProductRepository();

    public static void main(String[] args) {
        ProductEvent productEvent = setUpProductEvent();
        IProductCreator productCreator = new ProductCreator(productRepository, productEvent);
        Presentation presentation = new Console(productCreator);
        Presenter presenter = new Presenter(presentation);
        presenter.present();
    }

    private static ProductEvent setUpProductEvent() {
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        TransactionProductListener transactionProductListener = new TransactionProductListener(transactionRepository);
        ProductEvent productEvent = new ProductEvent();
        productEvent.getEventManager().subscribe(EventType.CREATION,transactionProductListener);
        productEvent.getEventManager().subscribe(EventType.UPDATE, transactionProductListener);
        productEvent.getEventManager().subscribe(EventType.ELIMINATION, transactionProductListener);
        productEvent.getEventManager().subscribe(EventType.UPDATE, new ProductQuantityListener(productRepository, productEvent));
        return productEvent;
    }
}