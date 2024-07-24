package com.project.ShopApp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @JsonProperty("phone_number")
    @NotBlank(message = "phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Password can not be blank")
    private String password;

    @Min(value = 1, message = "You must enter role's Id")
    @JsonProperty("role_id")
    private Long roleId;

}
