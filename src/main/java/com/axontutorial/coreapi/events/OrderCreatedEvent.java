package com.axontutorial.coreapi.events;

import lombok.Data;

import java.util.Objects;

// Aggregate will handle the commands
// It is in charge of deciding if order can be created.
// It will notify the rest of the application of its decision by publishing an event.
@Data
public class OrderCreatedEvent {

    private final String orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderCreatedEvent that = (OrderCreatedEvent) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" + "orderId='" + orderId + '\'' + '}';
    }
}
