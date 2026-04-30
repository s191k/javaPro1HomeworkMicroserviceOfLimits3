package org.example.controllers;

import org.example.dto.CancelRequest;
import org.example.dto.ConfirmRequest;
import org.example.dto.LimitResponse;
import org.example.dto.ReserveRequest;
import org.example.repository.UserLimitRepository;
import org.example.service.LimitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/limits")
public class LimitController {

    private final LimitService limitService;
    private final UserLimitRepository userLimitRepository;

    public LimitController(LimitService limitService, UserLimitRepository userLimitRepository) {
        this.limitService = limitService;
        this.userLimitRepository = userLimitRepository;
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestBody ReserveRequest request) {
        limitService.reserve(request.userId(), request.amount(), request.operationId());
        return ResponseEntity.status(HttpStatus.CREATED).body("Reserved successfully");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestBody ConfirmRequest request) {
        limitService.confirm(request.operationId());
        return ResponseEntity.ok("Confirmed successfully");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestBody CancelRequest request) {
        limitService.cancel(request.operationId());
        return ResponseEntity.ok("Cancelled successfully");
    }

    @GetMapping("/{userId}")
    public LimitResponse getBalance(@PathVariable Long userId) {
        return limitService.getBalance(userId);
    }
}
