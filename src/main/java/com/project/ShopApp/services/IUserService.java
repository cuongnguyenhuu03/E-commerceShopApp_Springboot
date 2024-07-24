package com.project.ShopApp.services;

import com.project.ShopApp.dtos.UserDTO;
import com.project.ShopApp.exceptions.DataNotFoundException;
import com.project.ShopApp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password, Long roleId) throws Exception;
}
