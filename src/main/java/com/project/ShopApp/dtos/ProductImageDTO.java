package com.project.ShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {

    @Size(min = 1, message = "Product's ID must be > 0")
    @JsonProperty("product_id")
    private Long productid;

    @Size(min = 5, max =200, message = "Image's name")
    @JsonProperty("image_url")
    private String imageUrl;
}
