package com.tus.healthcare.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/ping")
    public ResponseEntity<String> ping(@RequestBody Map<String, String> body) {
        return new ResponseEntity<>("Pong", HttpStatus.OK);
    }
}
