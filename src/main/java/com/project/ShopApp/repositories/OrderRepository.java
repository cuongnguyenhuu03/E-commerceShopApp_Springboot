package com.project.ShopApp.repositories;

import com.project.ShopApp.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE " +
            "(:startDate IS NULL OR o.orderDate >= :startDate) AND " +
            "(:keyword IS NULL OR :keyword = '' OR o.fullName LIKE %:keyword% OR o.address LIKE %:keyword% " +
            "OR o.note LIKE %:keyword%)")
    Page<Order> findOrders(
            @Param("startDate") LocalDate startDate,
            @Param("keyword") String keyword,
            Pageable pageable);
}
