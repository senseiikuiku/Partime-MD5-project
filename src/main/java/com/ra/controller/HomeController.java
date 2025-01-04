package com.ra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class HomeController {
    @GetMapping
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("Xin chao", HttpStatus.OK);
    }
}
