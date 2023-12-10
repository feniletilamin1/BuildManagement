package com.example.buildmanagement.Services;


import com.example.buildmanagement.Models.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    int create(Order order);
    void update(Order order);
    void delete(int id);
    Order findOneById(int id);
}
