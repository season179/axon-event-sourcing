package com.axontutorial.querymodel;

import com.axontutorial.coreapi.events.*;
import com.axontutorial.coreapi.queries.FindAllOrderedProductsQuery;
import com.axontutorial.coreapi.queries.Order;
import com.axontutorial.coreapi.queries.OrderUpdatesQuery;
import com.axontutorial.coreapi.queries.TotalProductsShippedQuery;
import org.reactivestreams.Publisher;

import java.util.List;

public interface OrdersEventHandler {
    void on(OrderCreatedEvent event);

    void on(ProductAddedEvent event);

    void on(ProductCountIncrementedEvent event);

    void on(ProductCountDecrementedEvent event);

    void on(ProductRemovedEvent event);

    void on(OrderConfirmedEvent event);

    void on(OrderShippedEvent event);

    List<Order> handle(FindAllOrderedProductsQuery query);

    Publisher<Order> handleStreaming(FindAllOrderedProductsQuery query);

    Integer handle(TotalProductsShippedQuery query);

    Order handle(OrderUpdatesQuery query);

    void reset(List<Order> orderList);
}
