package com.axontutorial.commandmodel.order;

import com.axontutorial.coreapi.commands.AddProductCommand;
import com.axontutorial.coreapi.commands.ConfirmOrderCommand;
import com.axontutorial.coreapi.commands.CreateOrderCommand;
import com.axontutorial.coreapi.commands.ShipOrderCommand;
import com.axontutorial.coreapi.events.*;
import com.axontutorial.coreapi.exceptions.DuplicateOrderLineException;
import com.axontutorial.coreapi.exceptions.OrderAlreadyConfirmedException;
import com.axontutorial.coreapi.exceptions.UnconfirmedOrderException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.HashMap;
import java.util.Map;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * The `@Aggregate` annotation is an Axon Spring specific annotation marking this class as an aggregate.
 * It will notify the framework that the required CQRS and Event Sourcing specific building blocks need to be instantiated for this OrderAggregate.
 */
@Aggregate
public class OrderAggregate {

    /**
     * As an aggregate will handle commands that are targeted to a specific aggregate instance,
     * we need to specify the identifier with the `@AggregateIdentifier` annotation.
     */
    @AggregateIdentifier
    private String orderId;
    private boolean orderConfirmed;

    @AggregateMember
    private Map<String, OrderLine> orderLines;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        apply(new OrderCreatedEvent(command.getOrderId()));
    }

    protected OrderAggregate() {
        // Required by Axon to build a default Aggregate prior to Event Sourcing
    }

    @CommandHandler
    public void handle(AddProductCommand command) {
        if (orderConfirmed) {
            throw new OrderAlreadyConfirmedException(orderId);
        }

        String productId = command.getProductId();
        if (orderLines.containsKey(productId)) {
            throw new DuplicateOrderLineException(productId);
        }
        apply(new ProductAddedEvent(orderId, productId));
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        if (orderConfirmed) {
            return;
        }

        apply(new OrderConfirmedEvent(orderId));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }

        apply(new OrderShippedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.orderConfirmed = false;
        this.orderLines = new HashMap<>();
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        this.orderConfirmed = true;
    }

    @EventSourcingHandler
    public void on(ProductAddedEvent event) {
        String productId = event.getProductId();
        this.orderLines.put(productId, new OrderLine(productId));
    }

    @EventSourcingHandler
    public void on(ProductRemovedEvent event) {
        this.orderLines.remove(event.getProductId());
    }
}
