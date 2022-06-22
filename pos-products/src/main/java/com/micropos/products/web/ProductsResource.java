package com.micropos.products.web;

import com.micropos.products.model.Product;
import com.micropos.products.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ProductsResource {

    private final ProductService productService;

    public ProductsResource(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "products", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Product>> listProducts() {
        List<Product> products = productService.products();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Product> showProductById(@PathVariable("productId") String productId) {
        Product product = productService.getProduct(productId);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
