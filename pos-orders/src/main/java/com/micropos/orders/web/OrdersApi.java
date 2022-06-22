package com.micropos.orders.web;

import com.micropos.orders.model.Cart;
import com.micropos.orders.model.Order;
import com.micropos.orders.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api")
public class OrdersApi {

    OrderService orderService;

    public OrdersApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "orders", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Order> postCart(
            @Valid @RequestBody Cart cart
    ) {
        Order order = orderService.generateOrder(cart);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

}
