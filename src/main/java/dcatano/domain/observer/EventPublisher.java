package dcatano.domain.observer;

public interface EventPublisher<T> {
    void publish(EventType eventType, Event<T> event);
}
