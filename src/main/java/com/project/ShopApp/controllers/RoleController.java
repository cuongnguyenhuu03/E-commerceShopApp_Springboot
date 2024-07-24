package com.project.ShopApp.controllers;
import com.project.ShopApp.models.Role;
import com.project.ShopApp.services.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping("")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.getAllRole();
        return ResponseEntity.ok(roles);
    }
}
