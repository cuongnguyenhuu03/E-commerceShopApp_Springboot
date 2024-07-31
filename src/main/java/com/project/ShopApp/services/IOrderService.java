package com.project.ShopApp.services;

import com.project.ShopApp.dtos.OrderDTO;
import com.project.ShopApp.exceptions.DataNotFoundException;
import com.project.ShopApp.models.Order;
import com.project.ShopApp.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;
    OrderResponse getOrder(Long id) throws DataNotFoundException;
    OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id);
    List<OrderResponse> findByUserId(Long userId);
    Page<Order> getOrdersByKeywordAndStartDay(LocalDate startDate, String keyword, PageRequest pageRequest);
}
