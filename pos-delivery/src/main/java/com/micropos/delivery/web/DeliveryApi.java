package com.micropos.delivery.web;

import com.micropos.delivery.model.OrderResult;
import com.micropos.delivery.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class DeliveryApi {

    DeliveryService deliveryService;

    public DeliveryApi(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @RequestMapping(value = "orders/{orderId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<OrderResult> getOrderStatus(
            @PathVariable(value = "orderId") int orderId
    ) {
        OrderResult result = deliveryService.getOrderResultById(orderId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
