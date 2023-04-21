package com.axontutorial.coreapi.queries;

import lombok.Getter;

import java.util.Objects;

public class Order {

    @Getter
    private final String orderId;

    @Getter
    private final String productId;

    @Getter
    private OrderStatus orderStatus;

    public Order(String orderId, String productId) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderStatus = OrderStatus.CREATED;
    }

    public void setOrderConfirmed() {
        this.orderStatus = OrderStatus.CONFIRMED;
    }

    public void setOrderShipped() {
        this.orderStatus = OrderStatus.SHIPPED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order that = (Order) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId) && orderStatus == that.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId, orderStatus);
    }

    @Override
    public String toString() {
        return "Order{" + "orderId='" + orderId + '\'' + ", productId=" + productId + ", orderStatus=" + orderStatus + '}';
    }
}
