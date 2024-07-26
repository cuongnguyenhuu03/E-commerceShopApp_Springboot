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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
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
    public OrderResponse getOrder(Long id) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Can not find Order with id  = "+id));
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Can not find Order with id  = "+id));
        User existingUser = userRepository.findById(orderDTO.getUserID())
                .orElseThrow(() -> new DataNotFoundException("Can not find User with id  = "+id));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order:: setId));
        modelMapper.map(orderDTO, order);
        order.setUser(existingUser);
        return modelMapper.map(orderRepository.save(order), OrderResponse.class);

    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        // no hard-delete -> sofl-delete
        if(order != null){
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<OrderResponse> findByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders){
            orderResponses.add(modelMapper.map(order, OrderResponse.class));
        }
        return orderResponses;
    }
}
