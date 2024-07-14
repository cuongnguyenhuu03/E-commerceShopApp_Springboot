package com.project.ShopApp.services;

import com.project.ShopApp.dtos.OrderDTO;
import com.project.ShopApp.exceptions.DataNotFoundException;
import com.project.ShopApp.responses.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;
    OrderResponse getOrder(Long id) throws DataNotFoundException;
    OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id);
    List<OrderResponse> findByUserId(Long userId);

}
