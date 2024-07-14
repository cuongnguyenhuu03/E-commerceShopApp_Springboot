package com.project.ShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    @JsonProperty("order_id")
    @Min(value = 1, message = "Order ID must be greater than 0")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be greater than 0")
    private Long productId;

    @Min(value = 0, message = "Price must be equal or greater than 0")
    private Long price;

    @JsonProperty("number_of_product")
    @Min(value = 1, message = "Price must be greater than 0")
    private int numberOfProduct;

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be equal greater than 0")
    private Float totalMoney;

    private String color;
}
