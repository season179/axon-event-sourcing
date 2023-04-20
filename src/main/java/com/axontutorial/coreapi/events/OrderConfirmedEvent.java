package com.axontutorial.coreapi.events;

import lombok.Data;

import java.util.Objects;

// Aggregate will handle the commands
// It is in charge of deciding if order is confirmed.
// It will notify the rest of the application of its decision by publishing an event.
@Data
public class OrderConfirmedEvent {

    private final String orderId;

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final OrderConfirmedEvent other = (OrderConfirmedEvent) obj;
        return Objects.equals(this.orderId, other.orderId);
    }

    @Override
    public String toString() {
        return "OrderConfirmedEvent{" + "orderId='" + orderId + '\'' + '}';
    }
}
