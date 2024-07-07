package dcatano.domain.observer;

public interface EventListener<T> {
    void update(EventType eventType, Event<T> product);
}
