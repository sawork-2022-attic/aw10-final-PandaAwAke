package com.micropos.delivery.service;

import com.micropos.delivery.model.Order;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

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
        System.out.println("accepted!");
        if (order != null) {
            System.out.println("Order received: " + order.getId() + ", timestamp: " + order.getTimestamp());
            receivedOrders.put(order.getId(), order);
        }
    }

    private static <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }




    @Bean
    public IntegrationFlow inGate() {
        return IntegrationFlows.from(
                    Http.inboundGateway("/check/{orderId}")
                            .headerExpression("orderId", "#pathVariables.orderId")
                ).headerFilter("accept-encoding", false)
//                .channel("orderChannel")
                .handle((payload, headers) -> {
                    Object id = headers.get("orderId");
                    int orderId = (id == null) ? -1 : Integer.parseInt((String) id);
                    if (receivedOrders.containsKey(orderId)) {
                        return receivedOrders.get(orderId);
                    }
                    return "Failed to receive this order!";
                })
                .get();
    }

//    @Bean
//    public IntegrationFlow outGate() {
//        return IntegrationFlows.from("orderChannel")
//                .handle(Http.outboundGateway("https://api.chucknorris.io/jokes/random")
//                        .httpMethod(HttpMethod.GET)
//                        .expectedResponseType(Joke.class))
//                .get();
//    }

}
