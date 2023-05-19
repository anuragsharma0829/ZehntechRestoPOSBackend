package com.restopos.security.services;

import com.restopos.models.OrderTable;
import com.restopos.models.Tables;

import java.util.Optional;

public interface OrderService {

    OrderTable createOrder(OrderTable orderTable);

    Optional<OrderTable> findByoId(Integer table_id);

}
