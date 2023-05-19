package com.restopos.security.services.impl;

import com.restopos.models.OrderTable;
import com.restopos.repository.OrderRepository;
import com.restopos.security.services.OrderService;
import com.restopos.security.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceimpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderTable createOrder(OrderTable orderTable) {
     return orderRepository.save(orderTable);
    }

    @Override
    public Optional<OrderTable> findByoId(Integer table_id) {
        return orderRepository.findById(table_id);
    }
}
