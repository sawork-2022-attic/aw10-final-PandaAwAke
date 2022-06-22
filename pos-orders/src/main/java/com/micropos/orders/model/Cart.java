package com.micropos.orders.model;

import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class Cart implements Serializable {

    @Valid
    @NotEmpty
    private final List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        return items.add(item);
    }
    public List<Item> getItems() {
        return items;
    }

}
