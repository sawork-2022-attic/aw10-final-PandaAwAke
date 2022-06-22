package com.micropos.carts.web;

import com.micropos.carts.model.Order;
import com.micropos.carts.model.OrderResult;
import com.micropos.carts.model.Product;
import com.micropos.carts.model.ProductCart;
import com.micropos.carts.service.CartsService;
import com.micropos.carts.service.RemoteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PosController {

    @Autowired
    private HttpSession session;

    @Autowired
    private CartsService cartsService;

    private RemoteServices remoteServices;

    private List<Product> products;


    public PosController() {
        ApplicationContext context = new ClassPathXmlApplicationContext("service_settings.xml");
        remoteServices = context.getBean("RemoteServices", RemoteServices.class);
    }


    @GetMapping("/")
    public String pos(Model model) {
        products = remoteServices.getProducts();

        setDefaultModelAttributes(model);
        return "index";
    }

    @GetMapping("/add")
    public String addProduct(
            @RequestParam(name = "pid") String pid,
            @RequestParam(name = "amount") int amount,
            Model model) {
        products = remoteServices.getProducts();

        for (Product product : products) {
            if (product.getId().equals(pid)) {
                cartsService.addProduct(getSessionCart(), product, amount);
                break;
            }
        }

        setDefaultModelAttributes(model);
        return "index";
    }

    @GetMapping("/remove")
    public String removeProduct(@RequestParam(value = "pid") String pid, Model model) {
        products = remoteServices.getProducts();
        cartsService.removeProduct(getSessionCart(), pid);

        setDefaultModelAttributes(model);
        return "index";
    }

    @GetMapping("/clear")
    public String clearCart(Model model) {
        products = remoteServices.getProducts();
        cartsService.clearCart(getSessionCart());

        setDefaultModelAttributes(model);
        return "index";
    }

    @GetMapping("/submit")
    public String submitCart(Model model) {
        Order order = remoteServices.postCart(getSessionCart().toCart());
        cartsService.clearCart(getSessionCart());

        model.addAttribute("info", "Your order id is " + order.getId() + "!");
        return "info";
    }


    @GetMapping("/orders/{orderId}")
    public String getOrderResult(@PathVariable("orderId") int orderId, Model model) {
        OrderResult result = remoteServices.getOrderResult(orderId);

        model.addAttribute("info", result.toString());
        return "info";
    }



    // Model Setting
    private void setDefaultModelAttributes(Model model) {
        ProductCart cart = getSessionCart();
        model.addAttribute("products", products);
        model.addAttribute("cart", cart);

        model.addAttribute("total", cartsService.calculateTotalPrice(cart));
    }


    // Session Operations
    private ProductCart getSessionCart() {
        ProductCart cart = (ProductCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ProductCart();
            saveSessionCart(cart);
        }
        return cart;
    }
    private void saveSessionCart(ProductCart cart) {
        session.setAttribute("cart", cart);
    }



}
