package com.project.ShopApp.services.impl;

import com.project.ShopApp.dtos.OrderDTO;
import com.project.ShopApp.exceptions.DataNotFoundException;
import com.project.ShopApp.models.Order;
import com.project.ShopApp.constant.OrderStatus;
import com.project.ShopApp.models.User;
import com.project.ShopApp.repositories.OrderRepository;
import com.project.ShopApp.repositories.UserRepository;
import com.project.ShopApp.responses.OrderResponse;
import com.project.ShopApp.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        // check if the user exists;
        User user = userRepository
                .findById(orderDTO.getUserID())
                .orElseThrow(() -> new DataNotFoundException(
                        "Can not find user with id: " + orderDTO.getUserID()));
        // convert orderDTO -> Order using ModelMapper
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now()); // Get the current times
        order.setStatus(OrderStatus.PENDING);
        // check shipping date
        LocalDate shipingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if(shipingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today");
        }
        order.setShippingDate(shipingDate);
        order.setActive(true);
        orderRepository.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<OrderResponse> findByUserId(Long userId) {
        return List.of();
    }
}
