package com.project.ShopApp.services.impl;

import com.project.ShopApp.dtos.OrderDetailDTO;
import com.project.ShopApp.exceptions.DataNotFoundException;
import com.project.ShopApp.models.Order;
import com.project.ShopApp.models.OrderDetail;
import com.project.ShopApp.models.Product;
import com.project.ShopApp.repositories.OrderDetailRepository;
import com.project.ShopApp.repositories.OrderRepository;
import com.project.ShopApp.repositories.ProductRepository;
import com.project.ShopApp.responses.OrderDetailResponse;
import com.project.ShopApp.responses.OrderResponse;
import com.project.ShopApp.services.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDetailResponse createOrderDetail( OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Can not find order with id: "+ orderDetailDTO.getOrderId()));

        Product product = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Can not find product with id: "+ orderDetailDTO.getProductId()));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .totalMoney(orderDetailDTO.getTotalMoney())
                .color(orderDetailDTO.getColor())
                .price(orderDetailDTO.getPrice())
                .numberOfProducts(orderDetailDTO.getNumberOfProducts())
                .build();


        orderDetailRepository.save(orderDetail);

        return modelMapper.map(orderDetailRepository.save(orderDetail), OrderDetailResponse.class) ;

    }

    @Override
    public OrderDetailResponse getOrderDetail(Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Can not find orderDetail with id: "+ id));
        OrderDetailResponse orderDetailResponse = modelMapper.map(orderDetail, OrderDetailResponse.class);
        return orderDetailResponse;
    }

    @Override
    @Transactional
    public OrderDetailResponse updateOrderDetail(
            Long id,
            OrderDetailDTO orderDetailDTO
    ) throws DataNotFoundException {
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: "+id));
        Order existingOrder = orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: "+id));
        Product existingProduct = productRepository.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.getProductId()));

        existingOrderDetail.setPrice(orderDetailDTO.getPrice());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        existingOrderDetail.setColor(orderDetailDTO.getColor());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        OrderDetail newOrderDetail = orderDetailRepository.save(existingOrderDetail);
        return modelMapper.map(newOrderDetail, OrderDetailResponse.class);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<OrderDetailResponse> findByOrderId(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
        for(OrderDetail orderDetail: orderDetails){
            orderDetailResponses.add(modelMapper.map(orderDetail, OrderDetailResponse.class));
        }
        return orderDetailResponses;
    }
}
