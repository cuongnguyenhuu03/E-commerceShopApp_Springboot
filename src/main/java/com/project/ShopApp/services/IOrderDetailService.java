package com.project.ShopApp.services;

import com.project.ShopApp.dtos.OrderDetailDTO;
import com.project.ShopApp.exceptions.DataNotFoundException;
import com.project.ShopApp.models.OrderDetail;
import com.project.ShopApp.responses.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetailResponse getOrderDetail(Long id) throws DataNotFoundException;
    OrderDetailResponse updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData)
            throws DataNotFoundException;
    void deleteById(Long id);
    List<OrderDetailResponse> findByOrderId(Long orderId);
}
