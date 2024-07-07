package dcatano.domain.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager<T> {
    Map<EventType, List<EventListener<T>>> listeners = new HashMap<>();

    public EventManager() {
        for (EventType eventType : EventType.values()) {
            this.listeners.put(eventType, new ArrayList<>());
        }
    }

    public void subscribe(EventType eventType, EventListener<T> listener) {
        listeners.get(eventType).add(listener);
    }

    public void unsubscribe(EventType eventType, EventListener<T> listener) {
        listeners.get(eventType).remove(listener);
    }

    public void notify(EventType eventType, Event<T> event) {
        List<EventListener<T>> eventListeners = listeners.get(eventType);
        for (EventListener<T> eventListener : eventListeners) {
            eventListener.update(eventType, event);
        }
    }
}
