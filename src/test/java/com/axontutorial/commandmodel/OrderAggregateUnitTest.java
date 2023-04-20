package com.axontutorial.commandmodel;

import com.axontutorial.commandmodel.order.OrderAggregate;
import com.axontutorial.coreapi.commands.CreateOrderCommand;
import com.axontutorial.coreapi.events.OrderCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class OrderAggregateUnitTest {

    private static final String ORDER_ID = UUID.randomUUID().toString();

    private FixtureConfiguration<OrderAggregate> fixture;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(OrderAggregate.class);
    }

    // When the aggregate handles the CreateOrderCommand, it should emit an OrderCreatedEvent.
    @Test
    void givenNoPriorActivity_whenCreateOrderCommand_thenCreateOrderEvent() {
        fixture.givenNoPriorActivity()
                .when(new CreateOrderCommand(ORDER_ID))
                .expectEvents(new OrderCreatedEvent(ORDER_ID));
    }
}
