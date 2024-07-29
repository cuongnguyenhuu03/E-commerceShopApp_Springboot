package com.project.ShopApp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ShopApp.models.OrderDetail;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse extends BaseResponse {
    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("fullname")
    private String fullName;

     @JsonProperty("phone_number")
    private String phoneNumber;

     private String address;

     private String note;

     @JsonProperty("order_date")
     private LocalDate orderDate;

     private String status;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    private LocalDate shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("order_details")
    private List<OrderDetail> orderDetails;

}
