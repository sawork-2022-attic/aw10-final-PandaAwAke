package com.micropos.delivery.service;

import com.micropos.delivery.model.Order;
import com.micropos.delivery.model.OrderResult;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.logging.Logger;

@Component
public class DeliveryService implements Consumer<Order> {

    private static final Map<Integer, Order> receivedOrders = new TreeMap<>();

    @Bean
    public Consumer<Order> handleOrder() {
        return new DeliveryService();
    }

    /**
     * Performs this operation on the given argument.
     *
     * @param order the input argument
     */
    @Override
    public void accept(Order order) {
        if (order != null) {
            Logger.getGlobal().info("Order received: " + order.getId() + ", timestamp: " + order.getTimestamp());
            receivedOrders.put(order.getId(), order);
        }
    }

    private static <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }

    public OrderResult getOrderResultById(int orderId) {
        Order order = null;
        if (receivedOrders.containsKey(orderId)) {
            order = receivedOrders.get(orderId);
        }
        if (order == null) {
            return new OrderResult();   // Invalid result
        }
        return new OrderResult(order);
    }



//    @Bean
//    public IntegrationFlow inGate() {
//        return IntegrationFlows.from(
//                    Http.inboundGateway("/api/orders/{orderId}")
//                            .headerExpression("orderId", "#pathVariables.orderId")
//                ).headerFilter("accept-encoding", false)
////                .channel("orderChannel")
//                .handle((payload, headers) -> {
//                    Object id = headers.get("orderId");
//                    int orderId = (id == null) ? -1 : Integer.parseInt((String) id);
//                    if (receivedOrders.containsKey(orderId)) {
//                        return receivedOrders.get(orderId);
//                    }
//                    return "Failed to receive this order!";
//                })
//                .get();
//    }

}
