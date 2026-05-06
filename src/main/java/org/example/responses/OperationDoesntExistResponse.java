package org.example.responses;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OperationDoesntExistResponse {

    private final String message;
    private final UUID operationId;

    public OperationDoesntExistResponse(String message, UUID operationId) {
        this.message = message;
        this.operationId = operationId;
    }
}
