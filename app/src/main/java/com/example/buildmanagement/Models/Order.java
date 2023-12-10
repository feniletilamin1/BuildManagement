package com.example.buildmanagement.Models;

import java.math.BigDecimal;
import java.util.Calendar;

public class Order {
    int id;
    Calendar addDate;
    String name;
    BigDecimal price;

    public Order(int id, Calendar addDate, String name, BigDecimal price) {
        this.id = id;
        this.addDate = addDate;
        this.name = name;
        this.price = price;
    }
    public Order(Calendar addDate, String name, BigDecimal price) {
        this.addDate= addDate;
        this.name = name;
        this.price = price;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getAddDate() {
        return addDate;
    }

    public void setAddDate(Calendar addDate) {
        this.addDate = addDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}