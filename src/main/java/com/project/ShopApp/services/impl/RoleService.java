package com.project.ShopApp.services.impl;

import com.project.ShopApp.models.Role;
import com.project.ShopApp.repositories.RoleRepository;
import com.project.ShopApp.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
