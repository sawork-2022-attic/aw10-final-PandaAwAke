package com.micropos.products.repository;

import com.micropos.products.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDRepository implements ProductRepository {
    private List<Product> products = null;

    private final CircuitBreaker readingListCircuitBreaker;

    public JDRepository(CircuitBreakerFactory circuitBreakerFactory) {
        this.readingListCircuitBreaker = circuitBreakerFactory.create("products");
    }

    private List<Product> defaultProducts() {
        List<Product> defaultResult = new ArrayList<Product>();
        defaultResult.add(new Product("13284888", "Java从入门到精通（第6版）（软件开发视频大讲堂） Java入门经典", 75.8, "https://img13.360buyimg.com/n1/s200x200_jfs/t1/186038/9/7947/120952/60bdd993E41eea7e2/48ab930455d7381b.jpg"));
        return defaultResult;
    }

    @Override
    public Flux<Product> allProducts() {
        Flux<Product> result;
        if (products == null) {
            result = Flux.fromIterable(readingListCircuitBreaker.run(
                    () -> parseJD("Java"), throwable -> defaultProducts()
            ));
        } else {
            result = Flux.fromIterable(products);
        }
        return result;
    }

    @Override
    public Mono<Product> findProduct(String productId) {
        return allProducts().filter(p -> p.getId().equals(productId)).single();
    }



    @Cacheable(value = "parseJD", key = "#keyword")
    public static List<Product> parseJD(String keyword) {
        String url = "https://search.jd.com/Search?keyword=" + keyword;
        Document document = null;
        try {
            document = Jsoup.parse(new URL(url), 10000);
        } catch (IOException e) {
            throw new RuntimeException("Failed to fetch");
        }
        Element element = document.getElementById("J_goodsList");
        Elements elements = element.getElementsByTag("li");
        List<Product> list = new ArrayList<>();

        for (Element el : elements) {
            String id = el.attr("data-spu");
            String img = "https:".concat(el.getElementsByTag("img").eq(0).attr("data-lazy-img"));
            String price = el.getElementsByAttribute("data-price").text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            if (title.indexOf("，") >= 0)
                title = title.substring(0, title.indexOf("，"));

            Product product = new Product(id, title, Double.parseDouble(price), img);
            list.add(product);
        }
        return list;
    }
}
