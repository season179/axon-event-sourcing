package com.axontutorial.coreapi.commands;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Objects;

@Data
public class ConfirmOrderCommand {

    @TargetAggregateIdentifier
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
        final ConfirmOrderCommand other = (ConfirmOrderCommand) obj;
        return Objects.equals(this.orderId, other.orderId);
    }

    @Override
    public String toString() {
        return "ConfirmOrderCommand{" + "orderId='" + orderId + '\'' + '}';
    }
}
