package com.example.buildmanagement.Repositories;


import com.example.buildmanagement.Models.Order;

import java.text.ParseException;
import java.util.List;

public interface OrderRepository {
    List<Order> findAll() throws ParseException;
    int save(Order order);
    int update(Order order);
    int delete(int id);
    Order findOneById(int id);
}
