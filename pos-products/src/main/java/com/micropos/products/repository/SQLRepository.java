package com.micropos.products.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.products.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//@Repository
//@Component
public class SQLRepository implements ProductRepository {

    private List<Product> products = null;


    private String JDBC_DRIVER;
    private String DB_URL;
    private String USER;
    private String PASS;
    private String DATABASE_TABLE_NAME;

    private Connection connection = null;
    private int fetchedItems = 0;
    private static final int ITEMS_TO_FETCH_ONCE = 40;


    @Override
    public Flux<Product> allProducts() {
        Flux<Product> result;
        if (products.isEmpty()) {
            try {
                result = Flux.fromIterable(fetchFromDatabase());
            } catch (Exception e) {
                result = Flux.empty();
            }
        } else {
            result = Flux.fromIterable(products);
        }
        return result;
    }

    @Override
    public Mono<Product> findProduct(String productId) {
        return allProducts().filter(p -> p.getId().equals(productId)).single();
    }



    public Collection<Product> fetchFromDatabase() throws Exception {
        LinkedList<Product> queue = new LinkedList<>();
        if (connection == null) {
            try {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PreparedStatement stmt = connection.prepareStatement(
                "SELECT main_cat, title, asin, category, imageURLHighRes FROM " + DATABASE_TABLE_NAME +
                        " LIMIT ?,?"
        );

        stmt.setInt(1, fetchedItems);
        stmt.setInt(2, ITEMS_TO_FETCH_ONCE);

        fetchedItems += ITEMS_TO_FETCH_ONCE;

        ResultSet result = stmt.executeQuery();

        while (result.next()) {
            Product product = new Product();
            product.setId(String.valueOf(result.getString("asin")));
            product.setName(result.getString("title"));
            product.setPrice(233.3);

            String imageURLHighResStr = result.getString("imageURLHighRes");
            ArrayList<String> imageURLs;
            ObjectMapper objectMapper = new ObjectMapper();
            imageURLs = objectMapper.readValue(imageURLHighResStr, ArrayList.class);

            if (imageURLs.size() > 0) {
                product.setImage(imageURLs.get(0));
            }
            queue.offer(product);
        }
        // 完成后关闭
        result.close();
        stmt.close();
        products = queue;
        return products;
    }


    public String getJDBC_DRIVER() {
        return JDBC_DRIVER;
    }

    public void setJDBC_DRIVER(String JDBC_DRIVER) {
        this.JDBC_DRIVER = JDBC_DRIVER;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public void setDB_URL(String DB_URL) {
        this.DB_URL = DB_URL;
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String USER) {
        this.USER = USER;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public String getDATABASE_TABLE_NAME() {
        return DATABASE_TABLE_NAME;
    }

    public void setDATABASE_TABLE_NAME(String DATABASE_TABLE_NAME) {
        this.DATABASE_TABLE_NAME = DATABASE_TABLE_NAME;
    }
}
