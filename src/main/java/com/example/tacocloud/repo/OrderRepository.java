package com.example.tacocloud.repo;

import com.example.tacocloud.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}
