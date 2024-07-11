package com.project.ShopApp.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data // toString 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    @NotEmpty(message = "Category'name can not be empty")
    private String name;
}
