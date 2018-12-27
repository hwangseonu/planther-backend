package me.mocha.planther.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/connect")
    public ResponseEntity<?> isConnect() {
        return ResponseEntity.ok(null);
    }

}
