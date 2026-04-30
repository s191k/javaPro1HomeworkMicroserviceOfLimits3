package org.example;

import jakarta.transaction.Transactional;
import org.example.service.LimitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class LimitServiceTest {

    @Autowired
    private LimitService limitService;

    @Test
    void shouldThrowExceptionWhenNotEnoughLimit() {
        Long userId = 1L;
        BigDecimal bigAmount = new BigDecimal("200000.00"); // Больше дефолтного
        UUID opId = UUID.randomUUID();

        assertThrows(RuntimeException.class, () -> {
            limitService.reserve(userId, bigAmount, opId);
        });
    }
}