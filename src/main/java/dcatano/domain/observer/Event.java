package dcatano.domain.observer;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Event<T> {
    private final Instant creationDate;
    private final T payload;

    public Event(T payload) {
        this.creationDate = Instant.now();
        this.payload = payload;
    }
}
