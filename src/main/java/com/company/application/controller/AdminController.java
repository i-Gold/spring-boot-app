package com.company.application.controller;

import com.company.application.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/admin/deleteUser/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok().body("Done");
    }

}
