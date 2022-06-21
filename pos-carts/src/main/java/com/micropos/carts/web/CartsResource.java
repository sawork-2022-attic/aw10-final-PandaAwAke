package com.micropos.carts.web;

import com.micropos.carts.model.Item;
import com.micropos.carts.service.CartsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class CartsResource {

    private final CartsService cartsService;

    public CartsResource(CartsService cartsService) {
        this.cartsService = cartsService;
    }

    @RequestMapping(value = "carts", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Item>> listCart() {
        List<Item> products = cartsService.getCart().getItems();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "carts/{productId}/{quantity}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<List<Item>> addProduct(@PathVariable String productId, @PathVariable int quantity) {
        if (cartsService.putProduct(productId, quantity)) {
            List<Item> products = cartsService.getCart().getItems();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "carts/{productId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Integer> getProductQuantity(@PathVariable String productId) {
        return new ResponseEntity<>(cartsService.getProductQuantity(productId), HttpStatus.OK);
    }

    @RequestMapping(value = "carts/{productId}/{quantity}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<List<Item>> deleteProduct(@PathVariable String productId, @PathVariable int quantity) {
        if (cartsService.deleteProduct(productId, quantity)) {
            List<Item> products = cartsService.getCart().getItems();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
