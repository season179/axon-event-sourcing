package com.axontutorial.commandmodel.order;

import com.axontutorial.coreapi.commands.ConfirmOrderCommand;
import com.axontutorial.coreapi.commands.CreateOrderCommand;
import com.axontutorial.coreapi.commands.ShipOrderCommand;
import com.axontutorial.coreapi.events.OrderConfirmedEvent;
import com.axontutorial.coreapi.events.OrderCreatedEvent;
import com.axontutorial.coreapi.events.OrderShippedEvent;
import com.axontutorial.coreapi.exceptions.UnconfirmedOrderException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

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

    /**
     * The aggregate will commence its life cycle upon handling the CreateOrderCommand in the OrderAggregate 'command handling constructor'.
     * To tell the framework that the given function is able to handle commands, we'll add the `@CommandHandler` annotation.
     * When handling the CreateOrderCommand, it will notify the rest of the application that an order was created by publishing the OrderCreatedEvent.
     *
     * @param command
     */
    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(command.getOrderId()));
    }

    // To be able to source an aggregate based on its events, Axon requires a default constructor.
    protected OrderAggregate() {
    }

    // The signature of the command and event sourcing handlers simply state handle(the-command) and on(the-event) to maintain a concise format.
    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.orderConfirmed = false;
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
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }
}
