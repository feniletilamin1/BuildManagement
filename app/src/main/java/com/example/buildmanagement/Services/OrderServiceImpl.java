package com.example.buildmanagement.Services;

import com.example.buildmanagement.Models.Order;
import com.example.buildmanagement.Repositories.OrderRepositoryImpl;

import java.util.List;

/**
 * Реализация интерфейса OrderService для работы с заказами.
 */
public class OrderServiceImpl implements OrderService {
    OrderRepositoryImpl orderRepository = new OrderRepositoryImpl();

    /**
     * Возвращает список всех заказов.
     *
     * @return список заказов
     */
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Создает новый заказ.
     *
     * @param order заказ для создания
     */
    @Override
    public int create(Order order) {
        int orderId = orderRepository.save(order);
        return orderId;
    }

    /**
     * Обновляет данные заказа.
     *
     * @param order заказ для обновления
     */
    @Override
    public void update(Order order) {
        orderRepository.update(order);
    }

    /**
     * Удаляет заказ по указанному идентификатору.
     *
     * @param id идентификатор заказа для удаления
     */
    @Override
    public void delete(int id) {
        orderRepository.delete(id);
    }

    /**
     * Возвращает заказ по указанному идентификатору.
     *
     * @param id идентификатор заказа
     * @return заказ
     */
    @Override
    public Order findOneById(int id) {
        return orderRepository.findOneById(id);
    }

}
