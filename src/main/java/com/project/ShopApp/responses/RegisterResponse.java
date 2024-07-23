package com.project.ShopApp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ShopApp.models.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
}
