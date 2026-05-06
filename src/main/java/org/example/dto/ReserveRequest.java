package org.example.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ReserveRequest(Long userId, BigDecimal amount, UUID operationId) {}