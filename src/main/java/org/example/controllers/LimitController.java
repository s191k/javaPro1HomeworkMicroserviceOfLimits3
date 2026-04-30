package org.example.controllers;

import org.example.dto.OperationRequest;
import org.example.dto.LimitResponse;
import org.example.dto.ReserveRequest;
import org.example.service.LimitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/limits")
public class LimitController {

    private final LimitService limitService;

    public LimitController(LimitService limitService) {
        this.limitService = limitService;
    }

    @PostMapping("/reserve")
    @ResponseStatus(HttpStatus.CREATED)
    public void reserve(@RequestBody ReserveRequest request) {
        limitService.reserve(request);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestBody OperationRequest request) {
        limitService.confirm(request);
        return ResponseEntity.ok("Confirmed successfully");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestBody OperationRequest request) {
        limitService.cancel(request);
        return ResponseEntity.ok("Cancelled successfully");
    }

    @GetMapping("/{userId}")
    public LimitResponse getBalance(@PathVariable Long userId) {
        return limitService.getBalance(userId);
    }
}
